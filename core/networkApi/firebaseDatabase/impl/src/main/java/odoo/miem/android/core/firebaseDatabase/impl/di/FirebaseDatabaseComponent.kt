package odoo.miem.android.core.firebaseDatabase.impl.di

import dagger.Component
import odoo.miem.android.core.networkApi.firebaseDatabase.api.di.IFirebaseDatabaseApi

/**
 * [FirebaseDatabaseComponent] - **Dagger** component, which implements interface [IFirebaseDatabaseApi]
 * Providing in general **DI graph** with a help of [FirebaseDatabaseApiProvider].
 *
 * Included modules:
 *  - [FirebaseDatabaseModule] - provides [FirebaseDatabase] in *DI graph*
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        FirebaseDatabaseModule::class
    ]
)
interface FirebaseDatabaseComponent : IFirebaseDatabaseApi {

    companion object {
        fun create(): IFirebaseDatabaseApi = DaggerFirebaseDatabaseComponent.builder()
            .build()
    }
}
