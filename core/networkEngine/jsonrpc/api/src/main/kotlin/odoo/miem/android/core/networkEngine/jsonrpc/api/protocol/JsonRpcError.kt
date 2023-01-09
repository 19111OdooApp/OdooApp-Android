package odoo.miem.android.core.networkEngine.jsonrpc.api.protocol

data class JsonRpcError(
    val message: String,
    val code: Int,
    val data: Any?
)