package odoo.miem.android.core.jsonrpc.converter.api

import java.lang.reflect.Type

/**
 * [IDeserializer] is a parser of result
 *
 * @author Vorozhtsov Mikhail
 */
interface IDeserializer {

    /**
     * This method is used in [JsonRpcHelpers] and
     * parses and converts [data] by [type] to generic result [T]
     */
    fun <T> deserialize(type: Type, data: Any): T?

    /**
     * This method parses and converts [str] by [type] class to generic result [T]?
     */
    fun <T> deserialize(clazz: Class<out T>, str: String): T?

    /**
     * This method parses and converts [data] by [type] class to generic result [T]?
     */
    fun <T> deserialize(clazz: Class<out T>, byteArray: ByteArray): T?

    /**
     * This method parse and converts [data] by List<[listType]> class to generic result List<[T]>?
     */
    fun <T> deserialize(listType: Type, data: String): List<T>?
}
