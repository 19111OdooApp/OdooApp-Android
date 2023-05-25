package odoo.miem.android.core.jsonrpc.converter.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.jsonrpc.converter.api.IDeserializer
import odoo.miem.android.core.jsonrpc.converter.api.ISerializer

/**
 * [IConverterApi] needed for wrapping over [ISerializer], [IDeserializer] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IConverterApi : Api {

    val serializer: ISerializer

    val deserializer: IDeserializer
}
