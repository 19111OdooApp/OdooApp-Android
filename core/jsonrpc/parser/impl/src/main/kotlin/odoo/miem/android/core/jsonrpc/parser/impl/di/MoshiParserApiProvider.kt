package odoo.miem.android.core.jsonrpc.parser.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.jsonrpc.parser.api.di.IParserApi

/**
 * [MoshiParserApiProvider] - **Dagger** module for providing
 * [MoshiParserComponent] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class MoshiParserApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IParserApi::class)
    fun provideMoshiParserComponent() = ApiProvider { MoshiParserComponent.create() }
}
