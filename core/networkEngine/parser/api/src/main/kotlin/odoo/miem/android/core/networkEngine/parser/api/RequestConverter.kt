package odoo.miem.android.core.networkEngine.parser.api

import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcRequest

interface RequestConverter {

    fun convert(request: JsonRpcRequest): String
}