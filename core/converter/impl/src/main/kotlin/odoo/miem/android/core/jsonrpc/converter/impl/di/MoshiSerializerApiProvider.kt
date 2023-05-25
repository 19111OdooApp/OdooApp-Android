package odoo.miem.android.core.jsonrpc.converter.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.jsonrpc.converter.api.di.IConverterApi

/**
 * [MoshiSerializerApiProvider] - **Dagger** module for providing
 * [MoshiConverterComponent] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class MoshiSerializerApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IConverterApi::class)
    fun provideMoshiParserComponent() = ApiProvider { MoshiConverterComponent.create() }
}
