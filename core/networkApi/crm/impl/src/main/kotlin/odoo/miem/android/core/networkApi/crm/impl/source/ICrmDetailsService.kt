package odoo.miem.android.core.networkApi.crm.impl.source

import odoo.miem.android.core.jsonRpcApiFabric.api.JsonRpcApi
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpc
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcArgument
import odoo.miem.android.core.jsonrpc.base.engine.annotation.JsonRpcPath
import odoo.miem.android.core.networkApi.crm.api.entities.CrmApplicationDetailsResponse

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
}
