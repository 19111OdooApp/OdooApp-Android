package odoo.miem.android.core.networkEngine.parser.api

import java.lang.reflect.Type

interface ResultParser {

    fun <T> parse(type: Type, data: Any): T
}