package odoo.miem.android.core.network.authorization

import io.reactivex.rxjava3.core.Observable
import nl.nl2312.xmlrpc.XmlRpc
import odoo.miem.android.core.retrofitApiFabric.api.RetrofitApi
import retrofit2.http.Body
import retrofit2.http.POST

interface IGeneralAuthorization : RetrofitApi {

    @JvmSuppressWildcards
    @XmlRpc(METHOD)
    @POST(AUTH_PATH)
    fun authorization(
        @Body body: List<Any> = emptyList()
    ): Observable<Int>

    private companion object {
        const val AUTH_PATH = "/xmlrpc/2/common"
        const val METHOD = "authenticate"
    }
}