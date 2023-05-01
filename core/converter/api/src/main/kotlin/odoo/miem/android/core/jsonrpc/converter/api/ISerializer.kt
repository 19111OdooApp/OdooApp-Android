package odoo.miem.android.core.jsonrpc.converter.api

/**
 * [ISerializer] is a converter of request
 *
 * @author Vorozhtsov Mikhail
 */
interface ISerializer {

    /**
     * This method serialize data from [clazz] with [data] to [String]
     */
    fun <T> serialize(clazz: Class<out T>, data: T): String
}
