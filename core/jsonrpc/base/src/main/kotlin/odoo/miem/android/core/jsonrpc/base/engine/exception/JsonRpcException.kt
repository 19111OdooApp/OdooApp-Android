package odoo.miem.android.core.jsonrpc.base.engine.exception

import odoo.miem.android.core.jsonrpc.base.parser.ResultParser

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