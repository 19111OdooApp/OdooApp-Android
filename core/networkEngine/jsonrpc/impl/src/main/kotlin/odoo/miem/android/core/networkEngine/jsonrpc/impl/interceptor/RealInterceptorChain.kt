package odoo.miem.android.core.networkEngine.jsonrpc.impl.interceptor

import odoo.miem.android.core.networkEngine.jsonrpc.api.JsonRpcClient
import odoo.miem.android.core.networkEngine.jsonrpc.api.JsonRpcInterceptor
import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcRequest
import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcResponse

data class RealInterceptorChain(
    private val client: JsonRpcClient,
    val interceptors: List<JsonRpcInterceptor>,
    private val request: JsonRpcRequest,
    private val index: Int = 0
) : JsonRpcInterceptor.Chain {

    override fun proceed(request: JsonRpcRequest): JsonRpcResponse {
        // Call the next interceptor in the chain. Last one in chain is ServerCallInterceptor.
        val nextChain = copy(index = index + 1, request = request)
        val nextInterceptor = interceptors[index]
        return nextInterceptor.intercept(nextChain)
    }

    override fun request(): JsonRpcRequest = request

    override fun toString(): String {
        return "RealInterceptorChain(index=$index, interceptors=$interceptors)"
    }
}