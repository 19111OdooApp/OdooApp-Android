package odoo.miem.android.core.firebaseDatabase.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.networkApi.firebaseDatabase.api.di.IFirebaseDatabaseApi

/**
 * [FirebaseDatabaseApiProvider] - **Dagger** module for providing
 * [FirebaseDatabaseComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class FirebaseDatabaseApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IFirebaseDatabaseApi::class)
    fun provideFirebaseDatabaseApiProvider() = ApiProvider {
        FirebaseDatabaseComponent.create()
    }
}
