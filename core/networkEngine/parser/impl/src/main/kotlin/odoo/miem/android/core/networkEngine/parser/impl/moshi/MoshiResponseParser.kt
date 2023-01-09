package odoo.miem.android.core.networkEngine.parser.impl.moshi

import com.squareup.moshi.Moshi
import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcResponse
import odoo.miem.android.core.networkEngine.parser.api.ResponseParser
import javax.inject.Inject

class MoshiResponseParser @Inject constructor(
    private val moshi: Moshi
) : ResponseParser {
    override fun parse(data: ByteArray): JsonRpcResponse {
        val responseType = JsonRpcResponse::class.java
        val adapter = moshi.adapter(responseType)
        return adapter.fromJson(data.decodeToString())
            ?: throw IllegalStateException("Unexpectedly null json parse result for value: $data!")
    }
}