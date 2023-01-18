package odoo.miem.android.common.network.authorization.impl.source

import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi

/**
 * [IGeneralAuthorization] - interface for making Retrofit instance of general authorization
 *
 * @author Vorozhtsov Mikhail
 */
interface IGeneralAuthorization : RetrofitApi {

    @JsonRpc("call")
    fun authorization(
        @JsonRpc("service") service: String,
        @JsonRpc("method") method: String = "login",
        @JsonRpc("args") args: List<Any> = emptyList()
    ): Int
}
