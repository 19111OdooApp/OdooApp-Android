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
}
