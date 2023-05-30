package odoo.miem.android.core.dataStore.impl

import android.content.Context
import androidx.core.content.edit
import odoo.miem.android.core.dataStore.api.IDataStore
import odoo.miem.android.core.dataStore.impl.preferences.delegates
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.platform.di.PlatformApi
import timber.log.Timber
import javax.inject.Inject

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
    override val isProdUrl: Boolean
        get() = url.contains(DEFAULT_PROD_DOMAIN)

    override fun setUrl(baseUrl: String) {
        if (baseUrl != url) {
            sharedPreferences.edit {
                putString(::url.name, baseUrl)
            }
            Timber.d("setUrl(): url = $url")
        } else {
            Timber.d("setUrl(): tried to set the same url")
        }
    }

    override val currentUID by sharedPreferences.delegates.int()
    override fun setUID(uid: Int) {
        if (uid != currentUID) {
            sharedPreferences.edit {
                putInt(::currentUID.name, uid)
            }
            Timber.d("setUID(): currentUID = $currentUID")
        } else {
            Timber.d("setUID(): tried to set the same uid")
        }
    }

    override val isAuthorized by sharedPreferences.delegates.boolean()
    override fun setAuthorized(authorized: Boolean) {
        if (authorized != isAuthorized) {
            sharedPreferences.edit {
                putBoolean(::isAuthorized.name, authorized)
            }
            Timber.d("setHseAuthorized(): authorized = $authorized")
        } else {
            Timber.d("setHseAuthorized(): already authorized")
        }
    }

    override val sessionId by sharedPreferences.delegates.string()
    override fun setSessionId(newSessionId: String) {
        if (newSessionId != sessionId) {
            sharedPreferences.edit {
                putString(::sessionId.name, newSessionId)
            }
            Timber.d("setSessionId(): sessionId = $newSessionId")
        } else {
            Timber.d("setSessionId(): tried to set the same session id")
        }
    }

    override val userModelId by sharedPreferences.delegates.int()
    override fun setUserModelId(newId: Int) {
        if (newId != userModelId) {
            sharedPreferences.edit {
                putInt(::userModelId.name, newId)
            }
            Timber.d("setUserModelId(): model id of user = $newId")
        } else {
            Timber.d("setUserModuleId(): tried to set the same model id")
        }
    }

    override val favouriteModules by sharedPreferences.delegates.stringSet()
    override fun setUserFavouriteModules(newFavouriteModules: Set<String>) {
        if (newFavouriteModules != favouriteModules) {
            sharedPreferences.edit {
                putStringSet(::favouriteModules.name, newFavouriteModules)
            }
            Timber.d("setUserFavoriteModules(): favourite modules = $newFavouriteModules")
        } else {
            Timber.d("setUserFavouriteModules(): tried to set the same modules")
        }
    }

    override fun clear() {
        sharedPreferences.all.clear()
    }

    private companion object {
        const val PREFERENCES_NAME = "dataStore"
        const val DEFAULT_PROD_DOMAIN = "erp.miem.hse.ru"
    }
}
