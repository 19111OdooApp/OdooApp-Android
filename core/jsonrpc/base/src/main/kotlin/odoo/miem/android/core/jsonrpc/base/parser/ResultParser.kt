package odoo.miem.android.core.jsonrpc.base.parser

import java.lang.reflect.Type

/**
 * [ResultParser] is a parser of result
 *
 * @author Vorozhtsov Mikhail
 */
interface ResultParser {

    /**
     * This method is used in [JsonRpcHelpers] and
     * parses and converts [data] by [type] to generic result [T]
     */
    fun <T> deserialize(type: Type, data: Any): T

    /**
     * This method parses and converts [data] by [type] class to generic result [T]?
     */
    fun <T> deserialize(type: Class<out T>, data: String): T?

    /**
     * This method parse and converts [data] by List<[listType]> class to generic result List<[T]>?
     */
    fun <T> deserializeList(listType: Type, data: String): List<T>?
}
