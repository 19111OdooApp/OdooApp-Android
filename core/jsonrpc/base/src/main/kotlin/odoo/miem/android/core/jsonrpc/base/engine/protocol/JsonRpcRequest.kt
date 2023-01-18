package odoo.miem.android.core.jsonrpc.base.engine.protocol

// TODO Description
data class JsonRpcRequest(
    val id: Long,
    val method: String,
    val params: Map<String, Any?> = emptyMap(),
    val jsonrpc: String = "2.0"
)