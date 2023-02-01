package odoo.miem.android.core.networkApi.authorization.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.authorization.api.source.UserInfoResponse

/**
 * [IGeneralAuthorization] - interface for making Retrofit instance of general authorization
 *
 * @author Vorozhtsov Mikhail
 */
interface IGeneralAuthorization : JsonRpcApi {

    @JsonRpc("call")
    fun authorization(
        @JsonRpcPath path: String = "session/authenticate",
        @JsonRpcArgument("db") database: String,
        @JsonRpcArgument("login") login: String,
        @JsonRpcArgument("password") password: String
    ): String

    @JsonRpc("call")
    fun userInfo(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "res.users.settings",
        @JsonRpcArgument("fields") fields: List<String> = listOf("id", "user_id")
    ): UserInfoResponse
}
