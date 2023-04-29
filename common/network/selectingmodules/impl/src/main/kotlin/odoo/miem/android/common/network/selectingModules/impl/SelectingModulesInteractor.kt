package odoo.miem.android.common.network.selectingModules.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.common.network.selectingModules.api.ISelectingModulesInteractor
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.common.network.selectingModules.impl.helpers.SelectingModulesHelper
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.firebaseDatabase.api.di.IFirebaseDatabaseApi
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.ModuleIconResponse
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserInfoRepositoryApi
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserModulesRepositoryApi
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooModulesResponse
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

    private val firebase by api(IFirebaseDatabaseApi::firebaseDatabase)

    private val helper = SelectingModulesHelper()

    override fun getUserInfo(): ResultSingle<User> {
        Timber.d("getUserInfo()")

        return userInfoRepository.getUserInfo()
            .map<Result<User>> { response ->
                val userInfo = helper.convertUserInfoResponse(response = response)

                Timber.d(
                    "getUserInfo(): id = ${userInfo.user.uid}, name = ${userInfo.user.name}"
                )
                dataStore.setUID(userInfo.user.uid)
                dataStore.setUserModelId(userInfo.user.modelId)

                processFavouriteModules(
                    userModelId = userInfo.user.uid,
                    newModules = userInfo.favouriteModules
                )

                SuccessResult(userInfo.user)
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
                userModulesRepository.fetchImplementedModules(),
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
                    favouriteModules = dataStore.favouriteModules.map { it.toInt() },
                    groups = groups
                )
            }
            .map<Result<List<OdooModule>>> { modules ->
                SuccessResult(modules)
            }
            .onErrorReturn {
                Timber.e("getOdooModules(): error message = ${it.message}")
                ErrorResult(R.string.selecting_modules_error)
            }
    }

    override fun updateFavouriteModules(
        userModelId: Int,
        favouriteModules: List<Int>
    ): ResultSingle<Boolean> {
        Timber.d("updateFavouriteModules()")
        dataStore.setUserFavouriteModules(favouriteModules.map { it.toString() }.toSet())

        return userInfoRepository.updateFavouriteModules(userModelId, favouriteModules)
            .map<Result<Boolean>> {
                SuccessResult(it)
            }
            .onErrorReturn {
                Timber.e("updateFavouriteModules(): error message = ${it.message}")
                ErrorResult(R.string.selecting_modules_error)
            }
    }

    private fun processFavouriteModules(userModelId: Int, newModules: List<Int>) {
        val currentFavouriteModules = dataStore.favouriteModules.map { it.toInt() }

        when {
            // if current and new modules are equal, do nothing
            currentFavouriteModules == newModules -> {}
            // if current modules list is empty, get from api
            currentFavouriteModules.isEmpty() && newModules.isNotEmpty() -> {
                dataStore.setUserFavouriteModules(
                    newModules
                        .map { it.toString() }
                        .toSet()
                )
            }
            // user is single source of truth
            else -> {
                updateFavouriteModules(
                    userModelId = userModelId,
                    favouriteModules = currentFavouriteModules
                )
            }
        }
    }
}
