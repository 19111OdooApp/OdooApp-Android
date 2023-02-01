package odoo.miem.android.core.networkApi.selectingModules.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooGroup
import odoo.miem.android.core.networkApi.selectingModules.api.source.UserInfoResponse

/**
 * [IGetBaseInfo] - interface for making Retrofit instance of getting modules
 *
 * @author Egor Danilov
 */
interface IGetBaseInfo: JsonRpcApi {

    @JsonRpc("call")
    fun getUserGroups(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String,
        @JsonRpcArgument("fields") fields: List<String>
    ): List<OdooGroup>

    @JsonRpc("call")
    fun getModules(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String,
        @JsonRpcArgument("fields") fields: List<String>
    ): List<OdooGroup>

    @JsonRpc("call")
    fun userInfo(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "res.users.settings",
        @JsonRpcArgument("fields") fields: List<String> = listOf("id", "user_id")
    ): UserInfoResponse
}