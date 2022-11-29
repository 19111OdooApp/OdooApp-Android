package odoo.miem.android.core.dataStore.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

/**
 * [DataStoreApiProvider] - **Dagger** module for providing
 * [DataStoreComponent] in general map
 *
 * @author Vorozhtso Mikhail
 */
@Module
class DataStoreApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IDataStoreApi::class)
    fun providesDataStoreApiProvider() = ApiProvider { DataStoreComponent.create() }
}
