package odoo.miem.android.core.jsonrpc.base.engine.exception

class NetworkRequestException(
    override val message: String?,
    override val cause: Throwable
) : RuntimeException(message, cause)