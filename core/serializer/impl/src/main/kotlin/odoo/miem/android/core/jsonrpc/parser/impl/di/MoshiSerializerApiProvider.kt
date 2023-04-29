package odoo.miem.android.core.jsonrpc.parser.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.jsonrpc.parser.api.di.ISerializerApi

/**
 * [MoshiSerializerApiProvider] - **Dagger** module for providing
 * [MoshiSerializerComponent] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class MoshiSerializerApiProvider {

    @Provides
    @IntoMap
    @ApiKey(ISerializerApi::class)
    fun provideMoshiParserComponent() = ApiProvider { MoshiSerializerComponent.create() }
}
