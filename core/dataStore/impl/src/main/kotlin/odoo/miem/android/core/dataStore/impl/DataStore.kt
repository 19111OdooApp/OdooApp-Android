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
    private val sharedPreferences by lazy {
        context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

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
            Timber.d("setUID(): currentUID = $currentUID")
        }
    }

    override val isAuthorized by sharedPreferences.delegates.boolean()
    override fun setAuthorized(authorized: Boolean) {
        if (authorized != isAuthorized) {
            sharedPreferences.edit {
                putBoolean(::isAuthorized.name, authorized)
            }
            Timber.d("setHseAuthorized(): authorized = $authorized")
        }
    }

    override val sessionId by sharedPreferences.delegates.string()
    override fun setSessionId(newSessionId: String) {
        if (newSessionId != sessionId) {
            sharedPreferences.edit {
                putString(::sessionId.name, newSessionId)
            }
            Timber.d("setSessionId(): sessionId = $newSessionId")
        }
    }

    override val username by sharedPreferences.delegates.string()
    override fun setUsername(newName: String) {
        if (newName != username) {
            sharedPreferences.edit {
                putString(::username.name, newName)
            }
            Timber.d("setUsername(): username = $newName")
        }
    }

    private companion object {
        const val PREFERENCES_NAME = "dataStore"
    }
}
