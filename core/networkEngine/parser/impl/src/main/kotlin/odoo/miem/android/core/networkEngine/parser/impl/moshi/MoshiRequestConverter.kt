package odoo.miem.android.core.networkEngine.parser.impl.moshi

import com.squareup.moshi.Moshi
import odoo.miem.android.core.networkEngine.jsonrpc.api.protocol.JsonRpcRequest
import odoo.miem.android.core.networkEngine.parser.api.RequestConverter
import javax.inject.Inject

class MoshiRequestConverter @Inject constructor(
    private val moshi: Moshi
) : RequestConverter {
    override fun convert(request: JsonRpcRequest): String {
        return moshi.adapter(JsonRpcRequest::class.java).toJson(request)
    }
}