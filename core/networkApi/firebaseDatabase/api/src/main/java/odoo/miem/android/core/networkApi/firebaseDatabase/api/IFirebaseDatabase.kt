package odoo.miem.android.core.networkApi.firebaseDatabase.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.ModuleIconResponse
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.StatusIconResponse

/**
 * [IFirebaseDatabase] - interface for wrapping data layer
 * logic, which is connected with Firebase Firestore and Storage
 *
 * @author Egor Danilov
 */
interface IFirebaseDatabase {

    fun fetchModuleIcons(): Single<List<ModuleIconResponse>>
    fun fetchStatusIcons(): Single<List<StatusIconResponse>>
}
