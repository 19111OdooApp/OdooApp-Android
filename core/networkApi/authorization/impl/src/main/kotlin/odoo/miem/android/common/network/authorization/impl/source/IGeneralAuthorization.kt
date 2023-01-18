package odoo.miem.android.common.network.authorization.impl.source

import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi

/**
 * [IGeneralAuthorization] - interface for making Retrofit instance of general authorization
 *
 * @author Vorozhtsov Mikhail
 */
interface IGeneralAuthorization : JsonRpcApi {

    @JsonRpc("call")
    fun authorization(
        @JsonArgument("service") service: String,
        @JsonArgument("method") method: String = "login",
        @JsonArgument("args") args: List<Any> = emptyList()
    ): Int
}
