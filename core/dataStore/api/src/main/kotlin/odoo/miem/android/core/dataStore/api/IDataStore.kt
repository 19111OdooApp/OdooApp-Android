package odoo.miem.android.core.dataStore.api

/**
 * [IDataStore] - interface for wrapping data store
 * properties, and helpers for future manipulation
 *
 * @author Vorozhtos Mikhail
 */
interface IDataStore {

    /**
     * Current server url property and setter
     */
    val url: String
    fun setUrl(baseUrl: String)

    /**
     * Current user UID property and setter
     */
    val currentUID: Int
    fun setUID(uid: Int)

    val isAuthorized: Boolean
    fun setAuthorized(authorized: Boolean)

    val sessionId: String
    fun setSessionId(newSessionId: String)

    /**
     * Model id of current user in Odoo database
     */
    val userModelId: Int
    fun setUserModelId(newId: Int)

    /**
     * Set of favourite modules of current user
     */
    val favouriteModules: Set<String>
    fun setUserFavouriteModules(newFavouriteModules: Set<String>)

    fun clear()
}
