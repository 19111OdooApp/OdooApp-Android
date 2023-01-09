package odoo.miem.android.core.networkEngine.parser.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkEngine.parser.api.RequestConverter
import odoo.miem.android.core.networkEngine.parser.api.ResponseParser
import odoo.miem.android.core.networkEngine.parser.api.ResultParser

/**
 * [IParserApi] needed for wrapping over [RequestConverter], [ResponseParser], [ResultParser] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IParserApi : Api {

    val requestConverter: RequestConverter

    val responseParser: ResponseParser

    val resultParser: ResultParser
}
