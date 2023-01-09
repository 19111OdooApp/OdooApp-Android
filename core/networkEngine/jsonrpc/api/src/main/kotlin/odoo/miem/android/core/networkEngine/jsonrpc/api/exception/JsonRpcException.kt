package odoo.miem.android.core.networkEngine.jsonrpc.api.exception

data class JsonRpcException(
    override val message: String,
    val code: Int,
    val data: Any?
) : RuntimeException(message)