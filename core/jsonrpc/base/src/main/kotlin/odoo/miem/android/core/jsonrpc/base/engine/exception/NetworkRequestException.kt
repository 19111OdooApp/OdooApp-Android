package odoo.miem.android.core.jsonrpc.base.engine.exception

// TODO Description
class NetworkRequestException(
    override val message: String?,
    override val cause: Throwable
) : RuntimeException(message, cause)