package odoo.miem.android.core.jsonrpc.engine.interceptor

import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcCaller
import odoo.miem.android.core.jsonrpc.base.engine.JsonRpcInterceptor
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse

class ServerCallInterceptor(private val client: JsonRpcCaller) : JsonRpcInterceptor {

    override fun intercept(chain: JsonRpcInterceptor.Chain): JsonRpcResponse {
        return client.call(chain.request())
    }
}