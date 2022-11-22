package odoo.miem.android.core.dataStore.impl

import android.content.Context
import androidx.core.content.edit
import odoo.miem.android.core.dataStore.api.IDataStore
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.platform.di.PlatformApi

// TODO Description
class DataStore : IDataStore {

    private val context by api(PlatformApi::context)
    private val sharedPreferences by lazy { context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE) }

    // TODO Add rx
    override fun setBaseUrl(baseUrl: String) {
        sharedPreferences.edit {
            putString(URL_KEY, baseUrl)
        }
    }

    private companion object {
        const val PREFERENCES_NAME = "dataStore"

        const val URL_KEY = "url"
    }
}