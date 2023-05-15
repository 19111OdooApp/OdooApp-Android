package odoo.miem.android.core.networkApi.recruitment.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentApplicationDetailsResponse

/**
 * [IRecruitmentDetailsService] - interface for making Retrofit instance of recruitment
 *
 * @author Vorozhtsov Mikhail
 */
interface IRecruitmentDetailsService : JsonRpcApi {

    @JsonRpc("call")
    fun getApplicationInfo(
        @JsonRpcPath path: String = "web/dataset/call_kw/read",
        @JsonRpcArgument("model") model: String = "hr.applicant",
        @JsonRpcArgument("method") method: String = "read",
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any> = emptyMap(),
        @JsonRpcArgument("args") args: List<Any>,
    ): List<RecruitmentApplicationDetailsResponse>
}
