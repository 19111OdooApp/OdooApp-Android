package odoo.miem.android.core.dataStore.impl.di

import dagger.Module
import dagger.Provides
import odoo.miem.android.core.dataStore.api.IDataStore
import odoo.miem.android.core.dataStore.impl.DataStore

/**
 * [DataStoreModule] - module for providing instance of [DataStore]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class DataStoreModule {

    @Provides
    fun provideDataStore(): IDataStore = DataStore()
}
