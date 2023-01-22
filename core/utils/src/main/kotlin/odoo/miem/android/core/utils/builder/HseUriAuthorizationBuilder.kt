package odoo.miem.android.core.utils.builder

import android.net.Uri
import odoo.miem.android.core.utils.network.RequestHelpers
import org.json.JSONObject

/**
 * [HseUriAuthorizationBuilder] is a special class with [Builder] of
 * authorization url of HSE/MIEM instance.
 *
 * @author Vorozhtsov Mikhail
 */
class HseUriAuthorizationBuilder internal constructor(
    private val builder: Builder
) {

    fun generateHseAuthorizationUrl(): String = uriBuilder
        .scheme(builder.scheme)
        .authority(builder.authority)
        .encodedPath(builder.path)
        .encodedQuery("$FIELD_REDIRECT_URI=${generateRedirectUrl()}")
        .appendQueryParameters(builder.queryParameters)
        .build()
        .toString()

    private fun Uri.Builder.appendQueryParameters(params: Map<String, String>) = apply {
        for ((key, value) in params) {
            appendQueryParameter(key, value)
        }
    }

    private fun generateRedirectUrl(): String =
        "$DEFAULT_HSE_SCHEME://${builder.baseDomain}/auth_oauth/signin"

    class Builder {
        internal var authority = DEFAULT_HSE_AUTHORITY
        internal var baseDomain = DEFAULT_HSE_DOMAIN
        internal var path = DEFAULT_HSE_AUTHORIZATION_PATH
        internal var queryParameters = DEFAULT_HSE_QUERY_PARAMS
        internal var scheme = DEFAULT_HSE_SCHEME

        fun setScheme(scheme: String): Builder = apply {
            this.scheme = scheme
        }

        fun setAuthority(authority: String): Builder = apply {
            this.authority = authority
        }

        fun appendPath(path: String): Builder = apply {
            this.path = path
        }

        fun setBaseDomain(inputUrl: String): Builder = apply {
            this.baseDomain = domainProcessing(inputUrl)
        }

        fun appendQueryParameter(key: String, value: String): Builder = apply {
            this.queryParameters[key] = value
        }

        private fun generateRedirectStateUrl(): String = "$DEFAULT_HSE_SCHEME://$baseDomain/web"

        fun build(): HseUriAuthorizationBuilder {
            queryParameters[FIELD_CLIENT_ID] = baseDomain
            queryParameters[FIELD_STATE] = JSONObject()
                .put(FIELD_STATE_DATABASE, RequestHelpers.databaseName)
                .put(FIELD_STATE_PAGE, 4)
                .put(FIELD_STATE_REDIRECT_URL, Uri.encode(generateRedirectStateUrl()))
                .toString()

            return HseUriAuthorizationBuilder(this)
        }
    }

    private companion object {
        val uriBuilder by lazy { Uri.Builder() }

        const val DEFAULT_HSE_SCHEME = "https"
        const val DEFAULT_HSE_DOMAIN = ""
        const val DEFAULT_HSE_AUTHORITY = "profile.miem.hse.ru"
        const val DEFAULT_HSE_AUTHORIZATION_PATH = "auth/realms/MIEM/protocol/openid-connect/auth"
        val DEFAULT_HSE_QUERY_PARAMS = mutableMapOf(
            "response_type" to "token",
            "client_id" to "",
            "scope" to "profile",
            "state" to ""
        )

        const val FIELD_CLIENT_ID = "client_id"
        const val FIELD_REDIRECT_URI = "redirect_uri"
        const val FIELD_STATE = "state"

        const val FIELD_STATE_DATABASE = "d"
        const val FIELD_STATE_PAGE = "p"
        const val FIELD_STATE_REDIRECT_URL = "r"
    }
}