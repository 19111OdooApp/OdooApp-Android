package odoo.miem.android.core.networkApi.userInfo.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.userInfo.api.source.UserInfoResponse

/**
 * [IUserInfoService] - interface for making Retrofit instance of user info
 *
 * @author Egor Danilov
 */
interface IUserInfoService : JsonRpcApi {

    @JsonRpc("call")
    fun getUserInfo(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "res.users.settings",
        @JsonRpcArgument("fields") fields: List<String> = userInfoFields
    ): UserInfoResponse

    private companion object {

        val userInfoFields = listOf("id", "user_id")
    }
}
