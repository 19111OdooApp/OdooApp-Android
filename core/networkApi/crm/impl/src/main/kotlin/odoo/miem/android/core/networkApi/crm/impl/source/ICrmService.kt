package odoo.miem.android.core.networkApi.crm.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.crm.api.entities.CrmtKanbanStagesResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmResponse

/**
 * [ICrmService] - interface for making Retrofit instance of crm
 *
 * @author Vorozhtsov Mikhail
 */
interface ICrmService : JsonRpcApi {

    // TODO Remake
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
            "name",
            "user_email",
            "activity_summary",
            "salary_proposed",
            "salary_expected",
            "create_date",
            "activity_state",
        )
    ): CrmResponse

    @JsonRpc("call")
    fun getRecruitmentKanbanStages(
        @JsonRpcPath path: String = "web/dataset/call_kw/web_read_group",
        @JsonRpcArgument("model") model: String = "hr.applicant",
        @JsonRpcArgument("method") method: String = "web_read_group",
        @JsonRpcArgument("args") args: List<Any> = emptyList(),
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any>
    ): CrmtKanbanStagesResponse

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
