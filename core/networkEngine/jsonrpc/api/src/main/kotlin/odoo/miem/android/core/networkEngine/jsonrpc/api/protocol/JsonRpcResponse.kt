package odoo.miem.android.core.networkEngine.jsonrpc.api.protocol

data class JsonRpcResponse(
    val id: Long,
    val result: Any?,
    val error: JsonRpcError?
)