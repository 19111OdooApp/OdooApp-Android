package odoo.miem.android.core.networkEngine.jsonrpc.impl.interceptor

import odoo.miem.android.core.networkEngine.jsonrpc.api.JsonRpcClient
import odoo.miem.android.core.networkEngine.jsonrpc.api.JsonRpcInterceptor
import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcResponse

class ServerCallInterceptor(private val client: JsonRpcClient) : JsonRpcInterceptor {

    override fun intercept(chain: JsonRpcInterceptor.Chain): JsonRpcResponse {
        return client.call(chain.request())
    }
}