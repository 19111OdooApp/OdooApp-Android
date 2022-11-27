package odoo.miem.android.core.utils.network

import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api

object RequestHelpers {

    private val dataStore by api(IDataStoreApi::dataStore)

    private val isHseUrl: Boolean
        get() = dataStore.url == Hse.HSE_URL

    fun authorizationRequestBody(
        login: String,
        password: String,
        args: Map<Any, Any> = emptyMap()
    ) = listOf(
        if (isHseUrl) Hse.HSE_DATABASE else dataStore.url.getDatabaseFromUrl(),
        login,
        password,
        args
    )

    // Crutch, but you know, this is backend of hse...
    private object Hse {
        const val HSE_URL = "https://odoo.miem.tv/"
        const val HSE_DATABASE = "crm"
    }
}

