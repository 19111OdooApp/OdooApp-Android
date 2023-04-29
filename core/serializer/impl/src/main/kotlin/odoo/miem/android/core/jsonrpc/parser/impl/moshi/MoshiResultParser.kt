package odoo.miem.android.core.jsonrpc.parser.impl.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import odoo.miem.android.core.jsonrpc.base.parser.ResultParser
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * [MoshiResultParser] is [Moshi] base implementation of [ResultParser]
 *
 * @author Vorozhtsov Mikhail
 */
class MoshiResultParser @Inject constructor(
    private val moshi: Moshi
) : ResultParser {

    override fun <T> deserialize(type: Type, data: Any): T {
        return moshi.adapter<T>(type).fromJsonValue(data)
            ?: error("Unexpectedly null json parse result for value: $data!")
    }

    override fun <T> deserialize(type: Class<out T>, data: String): T? {
        return moshi.adapter(type).fromJson(data)
    }

    override fun <T> deserializeList(listType: Type, data: String): List<T>? {
        val type = Types.newParameterizedType(List::class.java, listType)
        return moshi.adapter<List<T>>(type).fromJson(data)
    }
}
