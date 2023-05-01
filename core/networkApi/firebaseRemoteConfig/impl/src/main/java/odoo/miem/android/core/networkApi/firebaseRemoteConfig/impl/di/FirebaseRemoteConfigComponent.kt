package odoo.miem.android.core.networkApi.firebaseRemoteConfig.impl.di

import dagger.Component
import odoo.miem.android.core.networkApi.firebaseRemoteConfig.api.di.IFirebaseRemoteConfigApi

/**
 * [FirebaseRemoteConfigComponent] - **Dagger** component, which implements interface [IFirebaseRemoteConfigApi]
 * Providing in general **DI graph** with a help of [FirebaseRemoteConfigApiProvider].
 *
 * Included modules:
 *  - [FirebaseRemoteConfigModule] - provides [FirebaseRemoteConfig] in *DI graph*,
 *  - [RemoteConfigModule] - provides [RemoteConfig] in [FirebaseRemoteConfig]
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        FirebaseRemoteConfigModule::class,
        RemoteConfigModule::class
    ]
)
interface FirebaseRemoteConfigComponent : IFirebaseRemoteConfigApi {
    companion object {
        fun create(): IFirebaseRemoteConfigApi = DaggerFirebaseRemoteConfigComponent.builder()
            .build()
    }
}
