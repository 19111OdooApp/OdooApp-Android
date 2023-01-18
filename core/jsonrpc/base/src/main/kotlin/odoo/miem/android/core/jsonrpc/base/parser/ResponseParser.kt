package odoo.miem.android.core.jsonrpc.base.parser

import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse

/**
 * [ResponseParser] is a parser of response
 *
 * @author Vorozhtsov Mikhail
 */
interface ResponseParser {

    /**
     * This method parse and convert [data] to [JsonRpcResponse]
     */
    fun parse(data: ByteArray): JsonRpcResponse
}
