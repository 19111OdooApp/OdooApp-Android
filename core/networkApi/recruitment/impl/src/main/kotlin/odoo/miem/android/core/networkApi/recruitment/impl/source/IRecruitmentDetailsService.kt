package odoo.miem.android.core.networkApi.recruitment.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentApplicationDetailsResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentLogNoteResponse
import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentScheduleActivityResponse

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

    @JsonRpc("call")
    fun getLogNotes(
        @JsonRpcPath path: String = "mail/thread/messages",
        @JsonRpcArgument("limit") limit: Long = 30,
        @JsonRpcArgument("thread_model") model: String = "hr.applicant",
        @JsonRpcArgument("thread_id") threadId: Long,
    ): List<RecruitmentLogNoteResponse>

    @JsonRpc("call")
    fun createLogNote(
        @JsonRpcPath path: String = "mail/message/post",
        @JsonRpcArgument("thread_model") model: String = "hr.applicant",
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
    ): List<RecruitmentScheduleActivityResponse>
}
