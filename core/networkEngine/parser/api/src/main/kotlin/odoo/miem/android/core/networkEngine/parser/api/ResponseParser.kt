package odoo.miem.android.core.networkEngine.parser.api

import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcResponse

interface ResponseParser {

    fun parse(data: ByteArray): JsonRpcResponse
}