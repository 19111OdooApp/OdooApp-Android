package odoo.miem.android.core.networkEngine.jsonrpc.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.networkEngine.jsonrpc.api.di.IJsonRpcApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

/**
 * [JsonRpcApiProvider] - **Dagger** module for providing
 * [JsonRpcComponent] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class JsonRpcApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IJsonRpcApi::class)
    fun provideJsonRpcComponent() = ApiProvider { JsonRpcComponent.create() }
}
