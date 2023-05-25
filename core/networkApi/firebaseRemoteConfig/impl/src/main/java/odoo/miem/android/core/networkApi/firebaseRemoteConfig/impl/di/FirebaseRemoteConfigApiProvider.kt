package odoo.miem.android.core.networkApi.firebaseRemoteConfig.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.networkApi.firebaseRemoteConfig.api.di.IFirebaseRemoteConfigApi

/**
 * [FirebaseRemoteConfigApiProvider] - **Dagger** module for providing
 * [FirebaseRemoteConfigComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class FirebaseRemoteConfigApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IFirebaseRemoteConfigApi::class)
    fun provideRemoteConfigApiProvider() = ApiProvider {
        FirebaseRemoteConfigComponent.create()
    }
}
