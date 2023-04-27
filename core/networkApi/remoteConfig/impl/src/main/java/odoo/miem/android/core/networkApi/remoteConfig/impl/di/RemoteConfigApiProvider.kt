package odoo.miem.android.core.networkApi.remoteConfig.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.networkApi.remoteConfig.api.di.IRemoteConfigApi

/**
 * [RemoteConfigApiProvider] - **Dagger** module for providing
 * [RemoteConfigComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class RemoteConfigApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IRemoteConfigApi::class)
    fun provideRemoteConfigApiProvider() = ApiProvider {
        RemoteConfigComponent.create()
    }
}
