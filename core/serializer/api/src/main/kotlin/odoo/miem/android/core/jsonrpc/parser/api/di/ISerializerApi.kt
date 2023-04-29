package odoo.miem.android.core.jsonrpc.parser.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.jsonrpc.base.parser.RequestConverter
import odoo.miem.android.core.jsonrpc.base.parser.ResponseParser
import odoo.miem.android.core.jsonrpc.base.parser.ResultParser

/**
 * [ISerializerApi] needed for wrapping over [RequestConverter], [ResponseParser], [ResultParser] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface ISerializerApi : Api {

    val requestConverter: RequestConverter

    val responseParser: ResponseParser

    val resultParser: ResultParser
}
