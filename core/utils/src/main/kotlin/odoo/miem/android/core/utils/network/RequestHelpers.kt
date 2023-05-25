package odoo.miem.android.core.utils.network

import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.utils.regex.getDatabaseFromUrl

/**
 * [RequestHelpers] needs for wrapping request body in the desired format
 *
 * @author Vorozhtsov Mikhail
 */
object RequestHelpers {

    private val dataStore by api(IDataStoreApi::dataStore)

    private val isHseUrl: Boolean
        get() = dataStore.url.contains(Hse.HSE_DOMAIN)

    val databaseName: String
        get() = if (isHseUrl) Hse.HSE_DATABASE else dataStore.url.getDatabaseFromUrl()

    // Crutch, but you know, this is backend of hse...
    private object Hse {
        const val HSE_DOMAIN = "miem"
        const val HSE_DATABASE = "crm"
    }
}

