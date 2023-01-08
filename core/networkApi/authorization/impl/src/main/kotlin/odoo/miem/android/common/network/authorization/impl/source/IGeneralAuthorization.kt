package odoo.miem.android.common.network.authorization.impl.source

import io.reactivex.rxjava3.core.Observable
import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * [IGeneralAuthorization] - interface for making Retrofit instance of general authorization
 *
 * @author Vorozhtsov Mikhail
 */
interface IGeneralAuthorization : RetrofitApi {

    // TODO Move to Json RPC
    @JvmSuppressWildcards
    @POST(AUTH_PATH)
    fun authorization(
        @Body body: List<Any> = emptyList()
    ): Observable<Int>

    private companion object {
        const val AUTH_PATH = "/xmlrpc/2/common"
        const val METHOD = "authenticate"
    }
}
