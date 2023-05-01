package odoo.miem.android.core.firebaseDatabase.impl.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides

/**
 * [FirestoreModule] - **Dagger** module for providing [FirebaseFirestore] in general map
 *
 * @author Egor Danilov
 */
@Module
class FirestoreModule {

    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore
}