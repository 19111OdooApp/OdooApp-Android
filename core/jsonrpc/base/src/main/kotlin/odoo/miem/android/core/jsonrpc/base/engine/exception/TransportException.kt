package odoo.miem.android.core.jsonrpc.base.engine.exception

import okhttp3.Response

// TODO Description
class TransportException(
    val httpCode: Int,
    val response: Response,
    override val message: String?,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)