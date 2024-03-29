package odoo.miem.android.core.firebaseDatabase.impl.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * [FirestoreModule] - **Dagger** module for providing [FirebaseStorage] in general map
 *
 * @author Egor Danilov
 */
@Module
class StorageModule {

    @Provides
    @Singleton
    fun provideStorage(): FirebaseStorage = Firebase.storage
}
