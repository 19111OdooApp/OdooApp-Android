package odoo.miem.android.core.jsonrpc.parser.impl.moshi

import com.squareup.moshi.Moshi
import odoo.miem.android.core.jsonrpc.base.engine.protocol.JsonRpcRequest
import odoo.miem.android.core.jsonrpc.base.parser.RequestConverter
import javax.inject.Inject

// TODO Description
class MoshiRequestConverter @Inject constructor(
    private val moshi: Moshi
) : RequestConverter {
    override fun convert(request: JsonRpcRequest): String {
        return moshi.adapter(JsonRpcRequest::class.java).toJson(request)
    }
}