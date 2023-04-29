package odoo.miem.android.core.jsonrpc.parser.impl.di

import dagger.Component
import odoo.miem.android.core.jsonrpc.parser.api.di.ISerializerApi
import odoo.miem.android.core.jsonrpc.parser.impl.moshi.MoshiRequestConverter
import odoo.miem.android.core.jsonrpc.parser.impl.moshi.MoshiResponseParser
import odoo.miem.android.core.jsonrpc.parser.impl.moshi.MoshiResultParser

/**
 * [MoshiSerializerComponent] - **Dagger** component, which implements interface [ISerializerApi]
 * Providing in general **DI graph** with a help of [MoshiSerializerApiProvider].
 *
 * Included modules:
 *  - [MoshiParserModule] - provide [MoshiRequestConverter], [MoshiResponseParser]
 *  and [MoshiResultParser] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        MoshiModule::class,
        MoshiParserModule::class
    ]
)
interface MoshiSerializerComponent : ISerializerApi {
    companion object {
        fun create(): ISerializerApi = DaggerMoshiSerializerComponent.builder().build()
    }
}
