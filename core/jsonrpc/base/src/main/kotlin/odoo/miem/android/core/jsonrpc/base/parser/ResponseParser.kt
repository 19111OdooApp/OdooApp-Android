package odoo.miem.android.core.jsonrpc.base.parser

import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse

interface ResponseParser {

    fun parse(data: ByteArray): JsonRpcResponse
}