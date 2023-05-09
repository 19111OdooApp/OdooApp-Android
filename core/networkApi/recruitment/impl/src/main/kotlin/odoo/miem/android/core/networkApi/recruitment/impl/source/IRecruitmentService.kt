package odoo.miem.android.core.networkApi.recruitment.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentKanbanStagesResponse
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
        @JsonRpcArgument("domain") domain: List<Any>,
        @JsonRpcArgument("fields") fields: List<String> = listOf(
            "id",
            "priority",
            "job_id",
            "stage_id",
            "department_id",
            "user_id",
            "partner_name",
            "user_email",
            "activity_summary",
            "salary_proposed",
            "salary_expected",
            "create_date",
            "activity_state",
        )
    ): RecruitmentResponse

    @JsonRpc("call")
    fun getRecruitmentKanbanStages(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "hr.recruitment.stage",
        @JsonRpcArgument("fields") fields: List<String> = listOf(
            "id",
            "display_name",
            "job_ids"
        )
    ): RecruitmentKanbanStagesResponse

    @JsonRpc("call")
    fun changeStageInRecruitmentKanban(
        @JsonRpcPath path: String = "web/dataset/call_kw/write",
        @JsonRpcArgument("model") model: String = "hr.applicant",
        @JsonRpcArgument("method") method: String = "write",
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any> = emptyMap(),
        @JsonRpcArgument("args") args: List<Any>
    ): Boolean

    @JsonRpc("call")
    fun createNewKanbanStatus(
        @JsonRpcPath path: String = "web/dataset/call_kw/name_create",
        @JsonRpcArgument("model") model: String = "hr.recruitment.stage",
        @JsonRpcArgument("method") method: String = "name_create",
        @JsonRpcArgument("args") args: List<Any>,
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any>,
    ): List<Any> // [id, topic]
}
