package odoo.miem.android.core.dataStore.api.di

import odoo.miem.android.core.dataStore.api.IDataStore
import odoo.miem.android.core.di.api.Api

/**
 * [IDataStoreApi] needed for wrapping over [IDataStore] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IDataStoreApi : Api {

    val dataStore: IDataStore
}