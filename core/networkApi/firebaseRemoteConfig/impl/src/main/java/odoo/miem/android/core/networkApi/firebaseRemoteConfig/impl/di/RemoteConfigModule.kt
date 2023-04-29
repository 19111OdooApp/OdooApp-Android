package odoo.miem.android.core.networkApi.firebaseRemoteConfig.impl.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.Module
import dagger.Provides

/**
 * [RemoteConfigModule] - **Dagger** module for providing [FirebaseRemoteConfig] in general map
 *
 * @author Egor Danilov
 */
@Module
class RemoteConfigModule {

    @Provides
    fun provideRemoteConfig(): FirebaseRemoteConfig = Firebase.remoteConfig
}
