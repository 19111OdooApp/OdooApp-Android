package odoo.miem.android.core.jsonrpc.base.engine.exception

// TODO Description
data class JsonRpcException(
    override val message: String,
    val code: Int,
    val data: Any?
) : RuntimeException(message)