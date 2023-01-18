package odoo.miem.android.core.jsonrpc.base.engine.exception

import okhttp3.Response

/**
 * [NetworkRequestException] is exception, which thrown
 * when response is not success
 *
 * @author Vorozhtsov Mikhail
 */
class TransportException(
    val httpCode: Int,
    val response: Response,
    override val message: String?,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)