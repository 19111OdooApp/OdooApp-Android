package odoo.miem.android.core.jsonrpc.base.parser

import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcRequest

interface RequestConverter {

    fun convert(request: JsonRpcRequest): String
}