package odoo.miem.android.core.jsonrpc.converter.impl.moshi

import com.squareup.moshi.Moshi
import odoo.miem.android.core.jsonrpc.converter.api.ISerializer
import javax.inject.Inject

/**
 * [MoshiSerializer] is [Moshi] base implementation of [ISerializer]
 *
 * @author Vorozhtsov Mikhail
 */
class MoshiSerializer @Inject constructor(
    private val moshi: Moshi
) : ISerializer {

    override fun <T> serialize(clazz: Class<out T>, data: T): String {
        return moshi.adapter<T>(clazz).toJson(data)
    }
}
