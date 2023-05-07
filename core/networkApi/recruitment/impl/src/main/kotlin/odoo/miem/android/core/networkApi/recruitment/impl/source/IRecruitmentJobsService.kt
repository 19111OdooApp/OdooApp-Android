package odoo.miem.android.core.networkApi.recruitment.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentJobsResponse

/**
 * [IRecruitmentJobsService] - interface for making Retrofit instance of recruitment
 *
 * @author Vorozhtsov Mikhail
 */
interface IRecruitmentJobsService : JsonRpcApi {

    @JsonRpc("call")
    fun getRecruitmentJobs(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "hr.job",
        @JsonRpcArgument("fields") fields: List<String> = listOf(
            "name",
            "is_favorite",
            "department_id",
            "state",
            "no_of_recruitment",
            "new_application_count",
            "application_count",
            "website_url",
            "is_published"
        )
    ): RecruitmentJobsResponse
}
