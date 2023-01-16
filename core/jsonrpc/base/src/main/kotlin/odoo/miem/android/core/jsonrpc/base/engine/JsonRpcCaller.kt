package odoo.miem.android.core.jsonrpc.base.engine

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcRequest
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse

// TODO Description
interface JsonRpcCaller {

    fun call(jsonRpcRequest: JsonRpcRequest): JsonRpcResponse
}