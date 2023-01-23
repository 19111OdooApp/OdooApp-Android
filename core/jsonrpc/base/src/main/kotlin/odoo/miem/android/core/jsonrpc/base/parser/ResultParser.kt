package odoo.miem.android.core.jsonrpc.base.parser

import java.lang.reflect.Type

/**
 * [ResultParser] is a parser of result
 *
 * @author Vorozhtsov Mikhail
 */
interface ResultParser {

    /**
     * This method parse and convert [data] by [type] to generic result [T]
     */
    fun <T> parse(type: Type, data: Any): T
}
