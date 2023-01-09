package odoo.miem.android.core.networkEngine.jsonrpc.api.exception

import okhttp3.Response

class TransportException(
    val httpCode: Int,
    val response: Response,
    override val message: String?,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)