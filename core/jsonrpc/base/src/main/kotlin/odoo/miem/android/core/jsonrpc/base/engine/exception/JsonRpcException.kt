package odoo.miem.android.core.jsonrpc.base.engine.exception

/**
 * [JsonRpcException] is exception, which thrown
 * when [ResultParser] have problems with parsing
 * result of response
 *
 * @author Vorozhtsov Mikhail
 */
class JsonRpcException(
    override val message: String,
    val code: Int,
    val data: Any?
) : RuntimeException(message)
