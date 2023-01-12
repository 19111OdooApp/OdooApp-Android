package odoo.miem.android.core.jsonrpc.base.engine.protocol

data class JsonRpcError(
    val message: String,
    val code: Int,
    val data: Any?
)