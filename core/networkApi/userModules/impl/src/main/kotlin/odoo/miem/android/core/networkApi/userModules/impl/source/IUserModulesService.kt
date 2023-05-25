package odoo.miem.android.core.networkApi.userModules.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooModulesResponse

/**
 * [IUserModulesService] - interface for making Retrofit instance of getting modules
 *
 * @author Egor Danilov
 */
interface IUserModulesService : JsonRpcApi {

    @JsonRpc("call")
    fun getOdooGroups(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "res.groups",
        @JsonRpcArgument("fields") fields: List<String> = odooGroupsFields
    ): OdooGroupsResponse

    @JsonRpc("call")
    fun getOdooModules(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "ir.ui.menu",
        @JsonRpcArgument("fields") fields: List<String> = odooModulesFields
    ): OdooModulesResponse

    private companion object {

        val odooGroupsFields = listOf("id", "name", "users")
        val odooModulesFields = listOf("id", "name", "child_id", "parent_id", "groups_id")
    }
}
