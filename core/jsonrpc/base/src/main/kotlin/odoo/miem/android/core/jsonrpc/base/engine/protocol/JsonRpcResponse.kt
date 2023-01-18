package odoo.miem.android.core.jsonrpc.base.engine.protocol

// TODO Description
data class JsonRpcResponse(
    val id: Long,
    val result: Any?,
    val error: JsonRpcError?
)