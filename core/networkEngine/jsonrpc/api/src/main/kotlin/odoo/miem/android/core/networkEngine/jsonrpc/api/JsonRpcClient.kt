package odoo.miem.android.core.networkEngine.jsonrpc.api

import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcRequest
import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcResponse

// TODO Description
interface JsonRpcClient {
    fun call(jsonRpcRequest: JsonRpcRequest): JsonRpcResponse
}