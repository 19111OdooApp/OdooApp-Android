package odoo.miem.android.core.jsonrpc.base.parser

import java.lang.reflect.Type

interface ResultParser {

    fun <T> parse(type: Type, data: Any): T
}