package odoo.miem.android.core.jsonrpc.base.parser

import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcRequest

/**
 * [RequestConverter] is a converter of request
 *
 * @author Vorozhtsov Mikhail
 */
interface RequestConverter {

    /**
     * This method convert data from [JsonRpcRequest] to [String]
     */
    fun convert(request: JsonRpcRequest): String
}