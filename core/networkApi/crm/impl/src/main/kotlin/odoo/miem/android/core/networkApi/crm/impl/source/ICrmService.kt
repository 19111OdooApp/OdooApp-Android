package odoo.miem.android.core.networkApi.crm.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.crm.api.entities.CrmKanbanStagesResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmResponse

/**
 * [ICrmService] - interface for making Retrofit instance of crm
 *
 * @author Vorozhtsov Mikhail
 */
interface ICrmService : JsonRpcApi {

    @JsonRpc("call")
    fun getCrmInfo(
        @JsonRpcPath path: String = "web/dataset/search_read",
        @JsonRpcArgument("model") model: String = "crm.lead",
        @JsonRpcArgument("domain") domain: List<Any>,
        @JsonRpcArgument("fields") fields: List<String> = listOf(
            "id",
            "priority",
            "stage_id",
            "user_id",
            "partner_id",
            "name",
            "user_email",
            "activity_summary",
            "expected_revenue",
            "company_currency",
            "activity_state",
        )
    ): CrmResponse

    @JsonRpc("call")
    fun getCrmKanbanStages(
        @JsonRpcPath path: String = "web/dataset/call_kw/web_read_group",
        @JsonRpcArgument("model") model: String = "crm.lead",
        @JsonRpcArgument("method") method: String = "web_read_group",
        @JsonRpcArgument("args") args: List<Any> = emptyList(),
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any>
    ): CrmKanbanStagesResponse

    // TODO Remake
    @JsonRpc("call")
    fun changeStageInCrmKanban(
        @JsonRpcPath path: String = "web/dataset/call_kw/write",
        @JsonRpcArgument("model") model: String = "crm.lead",
        @JsonRpcArgument("method") method: String = "write",
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any> = emptyMap(),
        @JsonRpcArgument("args") args: List<Any>
    ): Boolean

    // TODO Remake
    @JsonRpc("call")
    fun createNewKanbanStatus(
        @JsonRpcPath path: String = "web/dataset/call_kw/name_create",
        @JsonRpcArgument("model") model: String = "crm.stage",
        @JsonRpcArgument("method") method: String = "name_create",
        @JsonRpcArgument("args") args: List<Any>,
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any>,
    ): List<Any> // [id, topic]
}
