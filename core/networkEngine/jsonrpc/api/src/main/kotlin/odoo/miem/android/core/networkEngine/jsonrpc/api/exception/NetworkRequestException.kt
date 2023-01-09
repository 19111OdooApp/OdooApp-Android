package odoo.miem.android.core.networkEngine.jsonrpc.api.exception

class NetworkRequestException(
    override val message: String?,
    override val cause: Throwable
) : RuntimeException(message, cause)