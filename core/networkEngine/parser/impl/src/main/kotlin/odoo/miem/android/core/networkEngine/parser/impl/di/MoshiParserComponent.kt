package odoo.miem.android.core.networkEngine.parser.impl.di

import dagger.Component
import odoo.miem.android.core.networkEngine.parser.api.di.IParserApi
import odoo.miem.android.core.networkEngine.parser.impl.moshi.MoshiRequestConverter
import odoo.miem.android.core.networkEngine.parser.impl.moshi.MoshiResponseParser
import odoo.miem.android.core.networkEngine.parser.impl.moshi.MoshiResultParser

/**
 * [MoshiParserComponent] - **Dagger** component, which implements interface [IParserApi]
 * Providing in general **DI graph** with a help of [MoshiParserApiProvider].
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
interface MoshiParserComponent : IParserApi {
    companion object {
        fun create(): IParserApi = DaggerMoshiParserComponent.builder().build()
    }
}
