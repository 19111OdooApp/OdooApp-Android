package odoo.miem.android.core.jsonrpc.base.engine.protocol

data class JsonRpcResponse(
    val id: Long,
    val result: Any?,
    val error: JsonRpcError?
)