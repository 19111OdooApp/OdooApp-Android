package odoo.miem.android.common.network.selectingModules.impl.helpers

import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.common.network.selectingModules.impl.entities.ImplementedModule
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

    private fun deserializeImplementedModules(jsonString: String): List<ImplementedModule>? {
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

        val user = User(
            modelId = modelId,
            uid = uid,
            name = name
        )

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

        val implementedModules = hashSetOf<String?>().apply {
            deserializeImplementedModules(implementedModulesJson)
                ?.map {
                    this.add(it.nameRu)
                    this.add(it.nameEn)
                }
        }

        val favouriteModulesSet = favouriteModules.toHashSet()

        val rootModules = mutableListOf<OdooModule>()

        // our backend is AWFUL, forgive me...
        // as Odoo API returns all modules in one list, we should build hierarch of modules..
        // TODO consider to remove if unnecessary
        val records = modules.records ?: emptyList()
        for (module in records) {
            val groupIds = module.groupIds?.toSet() ?: emptySet()
            if (groupsOfUser.intersect(groupIds).isNotEmpty()) {
                val parentId = (module.parentId?.firstOrNull() as? Double)?.toInt()

                val moduleId = module.id
                val moduleName = module.name

                val icon = moduleIcons.find {
                    moduleName == it.moduleNameEn || moduleName == it.moduleNameRu
                }

                if (moduleId != null && moduleName != null) {
                    rootModules.add(
                        OdooModule(
                            id = moduleId,
                            name = moduleName,
                            nameStandard = icon?.moduleNameEn ?: "",
                            iconDownloadUrl = icon?.downloadUrl ?: "",
                            parentId = parentId,
                            childModules = mutableListOf(),
                            isFavourite = moduleName in favouriteModulesSet,
                            isImplemented = moduleName in implementedModules
                        )
                    )
                }
            }
        }

        Timber.d(
            "getAvailableModulesOfUser(): rootModules = $rootModules, " +
                "implementedModules = $implementedModules," +
                " favouriteModules = $favouriteModulesSet"
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
        ?.filter { userUid in (it.users ?: emptyList()) }
        ?.mapNotNull { it.id }
        ?: emptyList()
}
