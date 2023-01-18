package odoo.miem.android.core.jsonrpc.parser.impl.moshi

import com.squareup.moshi.Moshi
import odoo.miem.android.core.jsonrpc.base.parser.ResultParser
import java.lang.reflect.Type
import javax.inject.Inject

// TODO Description
class MoshiResultParser @Inject constructor(
    private val moshi: Moshi
) : ResultParser {
    override fun <T> parse(type: Type, data: Any): T {
        return moshi.adapter<T>(type).fromJsonValue(data)
            ?: throw IllegalStateException("Unexpectedly null json parse result for value: $data!")
    }
}