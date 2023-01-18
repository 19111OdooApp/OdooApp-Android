package odoo.miem.android.core.jsonrpc.base.engine.exception

import okhttp3.OkHttpClient

/**
 * [TransportException] is exception, which thrown
 * when [OkHttpClient] have problems with calling
 * a new request
 *
 * @author Vorozhtsov Mikhail
 */
class NetworkRequestException(
    override val message: String?,
    override val cause: Throwable
) : RuntimeException(message, cause)