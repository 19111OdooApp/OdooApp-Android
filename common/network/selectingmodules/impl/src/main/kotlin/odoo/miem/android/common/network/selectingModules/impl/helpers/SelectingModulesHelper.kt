package odoo.miem.android.common.network.selectingModules.impl.helpers

import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.common.network.selectingModules.impl.entities.UserWithFavouriteModules
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooModulesResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.UserInfoResponse
import java.util.LinkedList
import java.util.Queue

internal class SelectingModulesHelper {

    fun convertUserInfoResponse(
        response: UserInfoResponse,
        deserializeFavouriteModules: (String) -> List<Int>? = { emptyList() }
    ): UserWithFavouriteModules {
        val record = response.records[0]

        val modelId = record.modelId
        val userInfo = record.userInfo
        val favouriteModules = record.favouriteModules

        val uid = (userInfo[0] as Double).toInt()
        val name = (userInfo[1] as String)
            .split(" ")
            .subList(0, 2)
            .joinToString(" ")

        val castedFavouriteModules = if (favouriteModules is String) {
            deserializeFavouriteModules(favouriteModules) ?: emptyList()
        } else {
            emptyList()
        }

        return UserWithFavouriteModules(
            user = User(modelId = modelId, uid = uid, name = name),
            favouriteModules = castedFavouriteModules
        )
    }

    fun getAvailableModulesOfUser(
        userUid: Int,
        modules: OdooModulesResponse,
        implementedModules: List<Int>,
        favouriteModules: List<Int>,
        groups: OdooGroupsResponse
    ): List<OdooModule> {
        val groupsOfUser = getGroupsOfUser(userUid, groups)
        val implementedModulesSet = implementedModules.toSortedSet()
        val favouriteModulesSet = favouriteModules.toSortedSet()

        val accessibleModules = mutableListOf<OdooModule>()

        // our backend is AWFUL, forgive me...
        // as Odoo API returns all modules in one list, we should build hierarch of modules..
        // TODO consider to remove if unnecessary
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
                        isFavourite = module.id in favouriteModulesSet,
                        isImplemented = module.id in implementedModulesSet
                    )
                )
            }
        }

        return buildModuleHierarchy(accessibleModules)
    }

    private fun buildModuleHierarchy(modules: MutableList<OdooModule>): List<OdooModule> {
        val moduleHierarchy = mutableListOf<OdooModule>()

        val rootModules = modules.filter { it.parentId == null }
        modules.removeAll(rootModules)

        val queue: Queue<OdooModule> = LinkedList()
        queue.addAll(rootModules)
        moduleHierarchy.addAll(rootModules)

        while (queue.isNotEmpty()) {
            val currentModule = queue.poll()
            val childModules = mutableListOf<OdooModule>()

            for (module in modules) {
                if (module.parentId == currentModule!!.id) {
                    childModules.add(module)
                    queue.offer(module)
                    modules.remove(module)
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