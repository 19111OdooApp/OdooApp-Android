package odoo.miem.android.core.networkApi.crm.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.crm.api.entities.CrmApplicationDetailsResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmLogNoteResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmScheduleActivityResponse

/**
 * [ICrmDetailsService] - interface for making Retrofit instance of recruitment
 *
 * @author Vorozhtsov Mikhail
 */
interface ICrmDetailsService : JsonRpcApi {

    @JsonRpc("call")
    fun getOpportunityInfo(
        @JsonRpcPath path: String = "web/dataset/call_kw/read",
        @JsonRpcArgument("model") model: String = "crm.lead",
        @JsonRpcArgument("method") method: String = "read",
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any> = emptyMap(),
        @JsonRpcArgument("args") args: List<Any>,
    ): List<CrmApplicationDetailsResponse>

    @JsonRpc("call")
    fun getLogNotes(
        @JsonRpcPath path: String = "mail/thread/messages",
        @JsonRpcArgument("limit") limit: Long = 30,
        @JsonRpcArgument("thread_model") model: String = "crm.lead",
        @JsonRpcArgument("thread_id") threadId: Long,
    ): List<CrmLogNoteResponse>

    @JsonRpc("call")
    fun createLogNote(
        @JsonRpcPath path: String = "mail/message/post",
        @JsonRpcArgument("thread_model") model: String = "crm.lead",
        @JsonRpcArgument("post_data") postData: Map<Any, Any>,
        @JsonRpcArgument("thread_id") threadId: Long,
    )

    @JsonRpc("call")
    fun getScheduleActivities(
        @JsonRpcPath path: String = "web/dataset/call_kw/activity_format",
        @JsonRpcArgument("model") model: String = "mail.activity",
        @JsonRpcArgument("method") method: String = "activity_format",
        @JsonRpcArgument("kwargs") kwargs: Map<Any, Any> = emptyMap(),
        @JsonRpcArgument("args") args: List<Any>,
    ): List<CrmScheduleActivityResponse>
}
