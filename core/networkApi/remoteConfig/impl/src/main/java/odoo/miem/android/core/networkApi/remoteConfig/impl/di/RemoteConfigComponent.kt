package odoo.miem.android.core.networkApi.remoteConfig.impl.di

import dagger.Component
import odoo.miem.android.core.networkApi.remoteConfig.api.di.IRemoteConfigApi

/**
 * [RemoteConfigComponent] - **Dagger** component, which implements interface [IRemoteConfigApi]
 * Providing in general **DI graph** with a help of [RemoteConfigApiProvider].
 *
 * Included modules:
 *  - [RemoteConfigModule] - provides [RemoteConfig] in *DI graph*
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        RemoteConfigModule::class
    ]
)
interface RemoteConfigComponent : IRemoteConfigApi {
    companion object {
        fun create(): IRemoteConfigApi = DaggerRemoteConfigComponent.builder()
            .build()
    }
}
