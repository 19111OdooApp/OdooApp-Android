package odoo.miem.android.core.networkApi.crm.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi

/**
 * [ICrmDetailsService] - interface for making Retrofit instance of recruitment
 *
 * @author Vorozhtsov Mikhail
 */
interface ICrmDetailsService : JsonRpcApi {

    // TODO Remake
//    @JsonRpc("call")
//    fun getApplicationInfo(
//        @JsonRpcPath path: String = "web/dataset/call_kw/read",
//        @JsonRpcArgument("model") model: String = "hr.applicant",
//        @JsonRpcArgument("method") method: String = "read",
//        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any> = emptyMap(),
//        @JsonRpcArgument("args") args: List<Any>,
//    ): List<RecruitmentApplicationDetailsResponse>
}
