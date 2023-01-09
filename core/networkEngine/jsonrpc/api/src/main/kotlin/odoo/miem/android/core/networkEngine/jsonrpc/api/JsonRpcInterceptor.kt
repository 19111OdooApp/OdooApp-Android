package odoo.miem.android.core.networkEngine.jsonrpc.api

import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcRequest
import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcResponse

// TODO Description
interface JsonRpcInterceptor {

    fun intercept(chain: Chain): JsonRpcResponse

    interface Chain {
        fun proceed(request: JsonRpcRequest): JsonRpcResponse

        fun request(): JsonRpcRequest
    }
}