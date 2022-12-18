package odoo.miem.android.core.dataStore.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.dataStore.api.IDataStore
import odoo.miem.android.core.dataStore.impl.DataStore

/**
 * [DataStoreModule] - module for providing instance of [DataStore]
 * in **DI graph**
 *
 * @author Vorozhtsov Mikhail
 */
@Module
interface DataStoreModule {

    @Binds
    fun provideDataStore(dataStore: DataStore): IDataStore
}
