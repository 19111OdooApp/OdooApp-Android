package odoo.miem.android.core.networkApi.userInfo.impl.helpers

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

internal class UserInfoSerializer {

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    inline fun <reified T> deserializeList(jsonString: String): List<T>? {
        return moshi.adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java))
            .fromJson(jsonString)
    }

    inline fun <reified T : Any> deserialize(dataClass: Class<out T>, json: String): T? {
        return moshi.adapter(dataClass).fromJson(json)
    }
}