package odoo.miem.android.core.jsonrpc.converter.impl.moshi

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import odoo.miem.android.core.jsonrpc.converter.api.IDeserializer
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * [MoshiDeserializer] is [Moshi] base implementation of [ResultParser]
 *
 * @author Vorozhtsov Mikhail
 */
class MoshiDeserializer @Inject constructor(
    private val moshi: Moshi
) : IDeserializer {

    override fun <T> deserialize(type: Type, data: Any): T? {
        return moshi.adapter<T>(type).fromJsonValue(data)
    }

    override fun <T> deserialize(clazz: Class<out T>, str: String): T? {
        return moshi.adapter(clazz).fromJson(str)
    }

    override fun <T> deserialize(clazz: Class<out T>, byteArray: ByteArray): T? {
        return moshi.adapter(clazz).fromJson(byteArray.decodeToString())
    }

    override fun <T> deserialize(listType: Type, data: String): List<T>? {
        val type = Types.newParameterizedType(List::class.java, listType)
        return moshi.adapter<List<T>>(type).fromJson(data)
    }
}
