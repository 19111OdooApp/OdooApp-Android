package odoo.miem.android.core.networkApi.firebaseDatabase.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkApi.firebaseDatabase.api.IFirebaseDatabase

/**
 * [IFirebaseDatabaseApi] needed for wrapping over [IFirebaseDatabaseApi] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IFirebaseDatabaseApi : Api {
    val firebaseDatabase: IFirebaseDatabase
}
