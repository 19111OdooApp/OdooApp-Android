package odoo.miem.android.core.networkApi.firebaseDatabase.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.FavouriteModulesResponse
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.ModuleIconResponse
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.StatusIconResponse

/**
 * [IFirebaseDatabase] - interface for wrapping data layer
 * logic, which is connected with Firebase Firestore and Storage
 *
 * @author Egor Danilov
 */
interface IFirebaseDatabase {

    /**
     * [fetchModuleIcons] - function for getting icons of Odoo modules
     * from Firebase Firestore and Storage
     *
     * @return [Single] with list of [ModuleIconResponse]
     */
    fun fetchModuleIcons(): Single<List<ModuleIconResponse>>

    /**
     * [fetchModuleIcons] - function for getting icons of status that used in
     * CRM and Recruitmend modules from Firebase Firestore and Storage
     *
     * @return [Single] with list of [StatusIconResponse]
     */
    fun fetchStatusIcons(): Single<List<StatusIconResponse>>

    /**
     * [fetchFavouriteModules] - function for getting favourite modules of user
     * Firebase Firestore
     *
     * @return [Single] with [FavouriteModulesResponse]
     */
    fun fetchFavouriteModules(uid: Int, userName: String): Single<FavouriteModulesResponse>

    /**
     * [addOrUpdateUser] - function that updates user data, if one exists in collection. Otherwise
     * creates new document
     *
     * @return [Single] with [Boolean] (true if add or update successful)
     */
    fun addOrUpdateUser(uid: Int, userName: String, favouriteModules: List<String>): Single<Boolean>
}
