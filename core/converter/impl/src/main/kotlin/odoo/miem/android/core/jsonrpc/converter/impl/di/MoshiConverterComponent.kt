package odoo.miem.android.core.jsonrpc.converter.impl.di

import dagger.Component
import odoo.miem.android.core.jsonrpc.converter.api.di.IConverterApi
import odoo.miem.android.core.jsonrpc.converter.impl.moshi.MoshiDeserializer
import odoo.miem.android.core.jsonrpc.converter.impl.moshi.MoshiSerializer
import javax.inject.Singleton

/**
 * [MoshiConverterComponent] - **Dagger** component, which implements interface [IConverterApi]
 * Providing in general **DI graph** with a help of [MoshiSerializerApiProvider].
 *
 * Included modules:
 *  - [MoshiParserModule] - provide [MoshiSerializer], [MoshiResponseParser]
 *  and [MoshiDeserializer] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Singleton
@Component(
    modules = [
        MoshiModule::class,
        MoshiConverterModule::class
    ]
)
interface MoshiConverterComponent : IConverterApi {
    companion object {
        fun create(): IConverterApi = DaggerMoshiConverterComponent.builder().build()
    }
}
