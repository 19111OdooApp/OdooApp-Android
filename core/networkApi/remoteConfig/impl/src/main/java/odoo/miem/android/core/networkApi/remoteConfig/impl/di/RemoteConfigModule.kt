package odoo.miem.android.core.networkApi.remoteConfig.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.networkApi.remoteConfig.api.IRemoteConfig
import odoo.miem.android.core.networkApi.remoteConfig.impl.RemoteConfig

/**
 * [RemoteConfigModule] - module for providing instance of [RemoteConfig]
 * in **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface RemoteConfigModule {

    @Binds
    fun provideSelectingModulesRepository(impl: RemoteConfig): IRemoteConfig
}
