package odoo.miem.android.common.network.selectingModules.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.common.network.selectingModules.api.ISelectingModulesInteractor
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.common.network.selectingModules.impl.entities.UserWithFavouriteModules
import odoo.miem.android.common.network.selectingModules.impl.helpers.SelectingModulesHelper
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.firebaseDatabase.api.di.IFirebaseDatabaseApi
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.ModuleIconResponse
import odoo.miem.android.core.networkApi.firebaseRemoteConfig.api.di.IFirebaseRemoteConfigApi
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserInfoRepositoryApi
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserModulesRepositoryApi
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooModulesResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.UserInfoResponse
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import javax.inject.Inject

/**
 * [SelectingModulesInteractor] - implementation of [ISelectingModulesInteractor]
 *
 * @author Egor Danilov
 */
class SelectingModulesInteractor @Inject constructor() : ISelectingModulesInteractor {

    private val userInfoRepository by api(IUserInfoRepositoryApi::userInfoRepository)
    private val userModulesRepository by api(IUserModulesRepositoryApi::selectingModulesRepository)

    private val dataStore by api(IDataStoreApi::dataStore)

    private val remoteConfig by api(IFirebaseRemoteConfigApi::remoteConfig)
    private val firebase by api(IFirebaseDatabaseApi::firebaseDatabase)

    private val helper = SelectingModulesHelper()

    override fun getUserInfo(): ResultSingle<User> {
        Timber.d("getUserInfo()")

        return userInfoRepository.getUserInfo()
            .map { response: UserInfoResponse ->
                val userInfo = helper.convertUserInfoResponse(response = response)

                Timber.d(
                    "getUserInfo(): uid = ${userInfo.uid}, name = ${userInfo.name}"
                )

                dataStore.setUID(userInfo.uid)
                dataStore.setUserModelId(userInfo.modelId)

                userInfo
            }
            .concatMap { user: User ->
                firebase.fetchFavouriteModules(uid = user.uid, userName = user.name)
                    .map { response ->
                        UserWithFavouriteModules(
                            user = user,
                            favouriteModules = response.modules
                        )
                    }
            }
            .concatMap { userWithFavouriteModules ->
                processFavouriteModules(
                    user = userWithFavouriteModules.user,
                    newModules = userWithFavouriteModules.favouriteModules
                ).map {
                    userWithFavouriteModules
                }
            }
            .map<Result<User>> { result: UserWithFavouriteModules ->

                SuccessResult(result.user)
            }
            .onErrorReturn {
                Timber.e("getUserInfo(): error message = ${it.message}")
                ErrorResult(R.string.selecting_modules_error)
            }
    }

    override fun getOdooModules(userUid: Int): ResultSingle<List<OdooModule>> {
        Timber.d("getOdooModules()")

        return Single
            .zip(
                userModulesRepository.getOdooModules(),
                userModulesRepository.getOdooGroups(),
                remoteConfig.fetchImplementedModules(),
                firebase.fetchModuleIcons(),
            ) { modules: OdooModulesResponse,
                groups: OdooGroupsResponse,
                implementedModulesJson: String,
                icons: List<ModuleIconResponse> ->

                helper.getAvailableModulesOfUser(
                    userUid = userUid,
                    modules = modules,
                    moduleIcons = icons,
                    implementedModulesJson = implementedModulesJson,
                    favouriteModules = dataStore.favouriteModules.toList(),
                    groups = groups
                )
            }
            .map<Result<List<OdooModule>>> { modules ->
                Timber.d("getOdooModules(): result = $modules")

                SuccessResult(modules)
            }
            .onErrorReturn {
                Timber.e("getOdooModules(): error message = ${it.message}")
                ErrorResult(R.string.selecting_modules_error)
            }
    }

    override fun updateFavouriteModules(
        user: User,
        favouriteModules: List<String>
    ): ResultSingle<Boolean> {
        Timber.d("updateFavouriteModules()")
        dataStore.setUserFavouriteModules(favouriteModules.toSet())

        return firebase.addOrUpdateUser(
            uid = user.uid,
            userName = user.name,
            favouriteModules = favouriteModules
        )
            .map<Result<Boolean>> {
                SuccessResult(it)
            }
            .onErrorReturn {
                Timber.e("updateFavouriteModules(): error message = ${it.message}")
                ErrorResult(R.string.selecting_modules_error)
            }
    }

    private fun processFavouriteModules(user: User, newModules: List<String>): Single<Boolean> {
        val currentFavouriteModules = dataStore.favouriteModules.toList()

        Timber.d("processFavouriteModules(): current $currentFavouriteModules new $newModules")

        return when {
            // if current and new modules are equal, do nothing
            currentFavouriteModules == newModules -> {
                Single.just(true)
            }
            // if there are no liked modules at user side, get it from firebase
            currentFavouriteModules.isEmpty() && newModules.isNotEmpty() -> {
                dataStore.setUserFavouriteModules(newModules.toSet())
                Single.just(true)
            }
            // else -> user if single source of truh
            else -> {
                firebase.addOrUpdateUser(
                    uid = user.uid,
                    userName = user.name,
                    favouriteModules = currentFavouriteModules
                )
            }
        }
    }
}
