package odoo.miem.android.common.network.selectingModules.impl.helpers

import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.common.network.selectingModules.impl.entities.ImplementedModules
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.jsonrpc.converter.api.di.IConverterApi
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.ModuleIconResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooModulesResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.UserInfoResponse
import timber.log.Timber
import java.util.LinkedList
import java.util.Queue

/**
 * [SelectingModulesHelper] - helper for converting raw data from [UserInfoRepository] and
 * [UserModulesRepository] to appropriate data classes for [SelectingModulesInteractor]
 *
 * @author Egor Danilov
 */
internal class SelectingModulesHelper {

    private val deserializer by api(IConverterApi::deserializer)

    fun deserializeFavouriteModules(jsonString: String): List<String>? {
        return deserializer.deserialize(
            listType = String::class.java,
            data = jsonString
        )
    }

    private fun deserializeImplementedModules(jsonString: String): List<String>? {
        return deserializer.deserialize(
            clazz = ImplementedModules::class.java,
            str = jsonString
        )?.modules
    }

    fun convertUserInfoResponse(response: UserInfoResponse): User {
        Timber.d("convertUserInfoResponse()")

        val record = response.records[0]

        val modelId = record.modelId
        val userInfo = record.userInfo

        val uid = (userInfo[0] as Double).toInt()
        val name = (userInfo[1] as String)
            .split(" ")
            .subList(0, 2)
            .joinToString(" ")

        val user = User(modelId = modelId, uid = uid, name = name)

        Timber.d("convertUserInfoResponse(): result = $user")
        return user
    }

    fun getAvailableModulesOfUser(
        userUid: Int,
        modules: OdooModulesResponse,
        moduleIcons: List<ModuleIconResponse>,
        implementedModulesJson: String,
        favouriteModules: List<String>,
        groups: OdooGroupsResponse
    ): List<OdooModule> {
        Timber.d("getAvailableModulesOfUser()")

        val groupsOfUser = getGroupsOfUser(userUid, groups)
        val moduleIconsMap = moduleIcons.associate { it.moduleName to it.downloadUrl }

        val implementedModulesSet = deserializeImplementedModules(implementedModulesJson)
            ?.toHashSet()
            ?: hashSetOf()

        val favouriteModulesSet = favouriteModules.toHashSet()

        val rootModules = mutableListOf<OdooModule>()

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

                rootModules.add(
                    OdooModule(
                        id = module.id,
                        name = module.name,
                        iconDownloadUrl = moduleIconsMap[module.name] ?: "",
                        parentId = parentId,
                        childModules = mutableListOf(),
                        isFavourite = module.name in favouriteModulesSet,
                        isImplemented = module.name in implementedModulesSet
                    )
                )
            }
        }

        Timber.d(
            "getAvailableModulesOfUser(): rootModules = $rootModules, " +
                "implementedModules = $implementedModulesSet, favouriteModules = $favouriteModulesSet"
        )
        return buildModuleHierarchy(rootModules)
    }

    private fun buildModuleHierarchy(modules: MutableList<OdooModule>): List<OdooModule> {
        val moduleHierarchy = mutableListOf<OdooModule>()

        val rootModules = modules.filter { it.parentId == null }
        modules.removeAll(rootModules)

        val queue: Queue<OdooModule> = LinkedList()
        queue.addAll(rootModules)
        moduleHierarchy.addAll(rootModules)

        val modulesToRemove = mutableListOf<OdooModule>()

        while (queue.isNotEmpty()) {
            val currentModule = queue.poll()
            val childModules = mutableListOf<OdooModule>()

            currentModule?.let {
                for (module in modules) {
                    if (it.id == module.parentId) {
                        childModules.add(module)
                        queue.offer(module)
                        modulesToRemove.add(module)
                    }
                }
                modules.removeAll(modulesToRemove)
                modulesToRemove.clear()
                it.childModules.addAll(childModules)
            }
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
