package odoo.miem.android.common.network.authorization.impl.source

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi

/**
 * [IGeneralAuthorization] - interface for making Retrofit instance of general authorization
 *
 * @author Vorozhtsov Mikhail
 */
interface IGeneralAuthorization : RetrofitApi {

    // TODO Move to Json RPC
    @JvmSuppressWildcards
    @JsonRpc("call")
    fun authorization(
        @JsonRpc("service") service: String,
        @JsonRpc("method") method: String = "login",
        @JsonRpc("args") args: List<Any> = emptyList()
    ): Int

    private companion object {
        const val AUTH_PATH = "/xmlrpc/2/common"
        const val METHOD = "authenticate"
    }
}
