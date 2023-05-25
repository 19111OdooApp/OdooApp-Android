package odoo.miem.android.core.networkApi.firebaseRemoteConfig.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.networkApi.firebaseRemoteConfig.api.IFirebaseRemoteConfig
import odoo.miem.android.core.networkApi.firebaseRemoteConfig.impl.FirebaseRemoteConfig

/**
 * [FirebaseRemoteConfigModule] - module for providing instance of [FirebaseRemoteConfig]
 * in **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface FirebaseRemoteConfigModule {

    @Binds
    fun provideFirebaseRemoteConfig(impl: FirebaseRemoteConfig): IFirebaseRemoteConfig
}
