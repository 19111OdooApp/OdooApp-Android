package odoo.miem.android.core.dataStore.impl.di

import dagger.Component
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.dataStore.impl.DataStore

/**
 * [DataStoreComponent] - **Dagger** component, which implements interface [IDataStoreApi]
 * Providing in general **DI graph** with a help of [DataStoreApiProvider].
 *
 * Included modules:
 *  - [DataStoreModule] - provide [DataStore] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        DataStoreModule::class
    ]
)
interface DataStoreComponent : IDataStoreApi {
    companion object {
        fun create(): IDataStoreApi = DaggerDataStoreComponent.builder().build()
    }
}
