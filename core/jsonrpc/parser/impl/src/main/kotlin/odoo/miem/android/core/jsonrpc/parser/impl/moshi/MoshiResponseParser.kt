package odoo.miem.android.core.jsonrpc.parser.impl.moshi

import com.squareup.moshi.Moshi
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcResponse
import odoo.miem.android.core.jsonrpc.base.parser.ResponseParser
import javax.inject.Inject

/**
 * [MoshiResponseParser] is [Moshi] base implementation of [ResponseParser]
 *
 * @author Vorozhtsov Mikhail
 */
class MoshiResponseParser @Inject constructor(
    private val moshi: Moshi
) : ResponseParser {
    override fun parse(data: ByteArray): JsonRpcResponse {
        val responseType = JsonRpcResponse::class.java
        val adapter = moshi.adapter(responseType)
        return adapter.fromJson(data.decodeToString())
            ?: error("Unexpectedly null json parse result for value: $data!")
    }
}
