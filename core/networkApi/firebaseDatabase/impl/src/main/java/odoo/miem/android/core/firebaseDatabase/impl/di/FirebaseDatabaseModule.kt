package odoo.miem.android.core.firebaseDatabase.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.firebaseDatabase.impl.FirebaseDatabase
import odoo.miem.android.core.networkApi.firebaseDatabase.api.IFirebaseDatabase

/**
 * [FirebaseDatabaseModule] - module for providing instance of [FirebaseDatabase]
 * in **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface FirebaseDatabaseModule {

    @Binds
    fun provideFirebaseDatabase(impl: FirebaseDatabase): IFirebaseDatabase
}
