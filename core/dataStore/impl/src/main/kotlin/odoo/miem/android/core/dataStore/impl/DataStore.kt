package odoo.miem.android.core.dataStore.impl

import android.content.Context
import androidx.core.content.edit
import odoo.miem.android.core.dataStore.api.IDataStore
import odoo.miem.android.core.dataStore.impl.preferences.delegates
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.platform.di.PlatformApi
import timber.log.Timber
import javax.inject.Inject

// TODO Maybe move to DataStore?
/**
 * [DataStore] - implementation of [IDataStore]
 *
 * @author Vorozhtsov Mikhail
 */
class DataStore @Inject constructor() : IDataStore {

    private val context by api(PlatformApi::context)
    private val sharedPreferences by lazy { context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE) }

    override val url by sharedPreferences.delegates.string()
    override fun setUrl(baseUrl: String) {
        if (baseUrl != url) {
            sharedPreferences.edit {
                putString(::url.name, baseUrl)
            }
            Timber.d("setUrl(): url = $url")
        }
    }

    override val currentUID by sharedPreferences.delegates.int()
    override fun setUID(uid: Int) {
        if (uid != currentUID) {
            sharedPreferences.edit {
                putInt(::currentUID.name, uid)
            }
            Timber.d("setUrl(): currentUID = $currentUID")
        }
    }

    private companion object {
        const val PREFERENCES_NAME = "dataStore"
    }
}
