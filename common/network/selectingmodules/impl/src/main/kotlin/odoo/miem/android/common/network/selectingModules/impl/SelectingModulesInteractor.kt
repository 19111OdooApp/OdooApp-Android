package odoo.miem.android.common.network.selectingModules.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.common.network.selectingModules.api.ISelectingModulesInteractor
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.selectingModules.api.di.ISelectingModulesRepositoryApi
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooModulesResponse
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserInfoRepositoryApi
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

    private val userInfoRepository by api(IUserInfoRepositoryApi::userInfoRepository)
    private val userModulesRepository by api(ISelectingModulesRepositoryApi::selectingModulesRepository)
    private val dataStore by api(IDataStoreApi::dataStore)

    override fun getUserInfo(): ResultSingle<User> {
        Timber.d("getUserInfo()")

        return userInfoRepository.getUserInfo()
            .map<Result<User>> { info ->
                val userInfo = info.records[0].userInfo

                val uid = (userInfo[0] as Double).toInt()
                val name = (userInfo[1] as String)
                    .split(" ")
                    .subList(0, 2)
                    .joinToString(" ")

                Timber.d("getUserInfo(): id = $uid, name = $name")
                dataStore.setUID(uid)

                SuccessResult(
                    User(
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

    private fun processAvailableModulesOfUser(
        userUid: Int,
        modules: OdooModulesResponse,
        groups: OdooGroupsResponse
    ): List<OdooModule> {
        val groupsOfUser = getGroupsOfUser(userUid, groups).toSet()
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
                val parentId = module.parentId

                if (parentId == currentModule!!.id) {
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
}