package odoo.miem.android.core.jsonrpc.engine.interceptor

import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcCaller
import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcInterceptor
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse

/**
 * [ServerCallInterceptor] is implementation of [JsonRpcInterceptor],
 * which is last of chain's list and make a call with a help of [JsonRpcCaller]
 *
 * @author Vorozhtsov Mikhail
 */
class ServerCallInterceptor(
    private val client: JsonRpcCaller,
    private val headers: Map<String, String> = emptyMap(),
    private val paths: List<String> = emptyList()
) : JsonRpcInterceptor {

    override fun intercept(chain: JsonRpcInterceptor.Chain): JsonRpcResponse {
        return client.call(
            jsonRpcRequest = chain.request(),
            headers = headers,
            paths = paths
        )
    }
}
