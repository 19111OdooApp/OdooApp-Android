package odoo.miem.android.common.network.selectingModules.impl

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import io.reactivex.rxjava3.core.Single
import odoo.miem.android.common.network.selectingModules.api.ISelectingModulesInteractor
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserInfoRepositoryApi
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserModulesRepositoryApi
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooModulesResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.UpdateFavouriteModulesRequest
import odoo.miem.android.core.utils.regex.getModulesIdFromFirebaseJson
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

/**
 * [SelectingModulesInteractor] - implementation of [ISelectingModulesInteractor]
 *
 * @author Egor Danilov
 */
class SelectingModulesInteractor @Inject constructor() : ISelectingModulesInteractor {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val userInfoRepository by api(IUserInfoRepositoryApi::userInfoRepository)
    private val userModulesRepository by api(IUserModulesRepositoryApi::selectingModulesRepository)
    private val dataStore by api(IDataStoreApi::dataStore)

    override fun getUserInfo(): ResultSingle<User> {
        Timber.d("getUserInfo()")

        return userInfoRepository.getUserInfo()
            .map<Result<User>> { info ->
                val modelId = info.records[0].modelId
                val userInfo = info.records[0].userInfo
                val favouriteModules = info.records[0].favouriteModules

                val uid = (userInfo[0] as Double).toInt()
                val name = (userInfo[1] as String)
                    .split(" ")
                    .subList(0, 2)
                    .joinToString(" ")

                Timber.d("getUserInfo(): id = $uid, name = $name")
                dataStore.setUID(uid)
                dataStore.setUserModelId(modelId)

                val castedFavouriteModules = if (favouriteModules is String) {
                    favouriteModules
                        .substring(1, favouriteModules.lastIndex)
                        .split(", ")
                        .map { it.toInt() }
                } else {
                    emptyList()
                }
                processFavouriteModules(
                    userModelId = modelId,
                    newModules = castedFavouriteModules
                )

                SuccessResult(
                    User(
                        modelId = modelId,
                        uid = uid,
                        name = name
                    )
                )
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
                userModulesRepository.getOdooGroups()
            ) { modules: OdooModulesResponse, groups: OdooGroupsResponse ->
                processAvailableModulesOfUser(
                    userUid = userUid,
                    modules = modules,
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

        val request = UpdateFavouriteModulesRequest(
            args = listOf(
                userModelId,
                mapOf(FAVOURITE_MODULES_KEY to favouriteModules)
            )
        )

        return userInfoRepository.updateFavouriteModules(request)
            .map<Result<Boolean>> {
                SuccessResult(it)
            }
            .onErrorReturn {
                Timber.e("updateFavouriteModules(): error message = ${it.message}")
                ErrorResult(R.string.selecting_modules_error)
            }
    }

    private fun processAvailableModulesOfUser(
        userUid: Int,
        modules: OdooModulesResponse,
        groups: OdooGroupsResponse
    ): List<OdooModule> {
        val groupsOfUser = getGroupsOfUser(userUid, groups).toSet()
        val implementedModules = fetchImplementedModules().toSortedSet()
        val favouriteModules = dataStore.favouriteModules
            .map { it.toInt() }
            .toSortedSet()

        val moduleHierarchy = mutableListOf<OdooModule>()
        val accessibleModules = mutableListOf<OdooModule>()

        // our backend is AWFUL, forgive me...
        for (module in modules.records) {
            if (groupsOfUser.intersect(module.groupIds.toSet()).isNotEmpty()) {
                val parentInfo = module.parentId

                val castedParentInfo = if (parentInfo is List<*>) {
                    parentInfo
                } else {
                    emptyList<Any>()
                }
                val parentId = if (castedParentInfo.isEmpty()) {
                    null
                } else {
                    (castedParentInfo[0] as Double).toInt()
                }

                accessibleModules.add(
                    OdooModule(
                        id = module.id,
                        name = module.name,
                        parentId = parentId,
                        childModules = mutableListOf(),
                        isFavourite = module.id in favouriteModules,
                        isImplemented = module.id in implementedModules
                    )
                )
            }
        }

        val rootModules = accessibleModules.filter { it.parentId == null }
        accessibleModules.removeAll(rootModules)

        val queue: Queue<OdooModule> = LinkedList()
        queue.addAll(rootModules)
        moduleHierarchy.addAll(rootModules)

        while (queue.isNotEmpty()) {
            val currentModule = queue.poll()
            val childModules = mutableListOf<OdooModule>()

            for (module in accessibleModules) {

                if (module.parentId == currentModule!!.id) {
                    childModules.add(module)
                    queue.offer(module)
                    accessibleModules.remove(module)
                }
            }
            currentModule!!.childModules.addAll(childModules)
        }

        return moduleHierarchy
    }

    private fun getGroupsOfUser(
        userUid: Int,
        groups: OdooGroupsResponse
    ): List<Int> = groups.records
        .filter { userUid in it.users }
        .map { it.id }

    private fun fetchImplementedModules(): List<Int> {
        val implementedModules = remoteConfig[IMPLEMENTED_MODULES_KEY]
            .asString()
            .getModulesIdFromFirebaseJson()

        Timber.d("fetchImplementedModules(): result = $implementedModules")

        return implementedModules
    }

    private fun processFavouriteModules(userModelId: Int, newModules: List<Int>) {
        val currentFavouriteModules = dataStore.favouriteModules
            .map { it.toInt() }

        when {
            currentFavouriteModules == newModules -> {} // if current and new modules are equal, do nothing
            currentFavouriteModules.isEmpty() && newModules.isNotEmpty() -> { // if current modules list is empty, get from api
                dataStore.setUserFavouriteModules(
                    newModules
                        .map { it.toString() }
                        .toSet()
                )
            }
            else -> { // user is single source of truth
                updateFavouriteModules(
                    userModelId = userModelId,
                    favouriteModules = currentFavouriteModules
                )
            }
        }
    }

    private companion object {
        const val IMPLEMENTED_MODULES_KEY = "implementedModules"
        const val FAVOURITE_MODULES_KEY = "x_favourite_modules"
    }
}
