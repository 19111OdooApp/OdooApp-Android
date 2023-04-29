package odoo.miem.android.core.networkApi.recruitment.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentResponse

/**
 * [IRecruitmentService] - interface for making Retrofit instance of recruitment
 *
 * @author Vorozhtsov Mikhail
 */
interface IRecruitmentService : JsonRpcApi {

    @JsonRpc("call")
    fun getRecruitmentInfo(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "hr.applicant",
    ): RecruitmentResponse
}
