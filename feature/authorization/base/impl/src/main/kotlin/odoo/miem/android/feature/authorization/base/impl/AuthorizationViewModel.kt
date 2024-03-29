package odoo.miem.android.feature.authorization.base.impl

import odoo.miem.android.common.network.authorization.api.di.IAuthorizationInteractorApi
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.builder.HseUriAuthorizationBuilder
import odoo.miem.android.core.utils.builder.urlProcessing
import odoo.miem.android.core.utils.regex.getSessionIdFromCookie
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.ResultSubject
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber

/**
 * [AuthorizationViewModel] handle major logic for [AuthorizationScreen]
 *
 * @author Vorozhtsov Mikhail, Alexander Lyutikov
 */
class AuthorizationViewModel : BaseViewModel() {

    private val authorizationInteractor by api(IAuthorizationInteractorApi::authorizationInteractor)
    private val dataStore by api(IDataStoreApi::dataStore)

    val authorizationState: ResultSubject<Unit> by lazyEmptyResultPublishSubject()

    init {
        if (dataStore.isAuthorized) {
            dataStore.clear()
        }
    }

    fun generalAuthorization(
        baseUrl: String,
        login: String,
        password: String
    ) {
        Timber.d("generalAuthorization(): baseUrl = $baseUrl, login = $login, password = $password")

        authorizationState.onLoadingState()
        authorizationInteractor
            .generalAuthorization(
                baseUrl = baseUrl,
                login = login,
                password = password
            ).schedule(
                authChannel,
                onSuccess = {
                    Timber.d("generalAuthorization(): result = $it")
                    authorizationState.onNext(it)
                },
                onError = Timber::e
            )
    }

    fun generateHseAuthorizationUrl(
        rawDomain: String
    ): String {
        dataStore.setUrl(urlProcessing(rawDomain))
        return HseUriAuthorizationBuilder.Builder()
            .setBaseDomain(rawDomain)
            .setClientID(
                DEFAULT_PROD_CLIENT_ID.takeIf { dataStore.isProdUrl }
            )
            .build()
            .generateHseAuthorizationUrl()
    }

    fun hseWebViewExitCondition(rawUrl: String, currentUrl: String?, cookie: String?): Boolean {
        return when {
            currentUrl == null -> false
            currentUrl.startsWith(urlProcessing(rawUrl)) -> {
                if (cookie == null) {
                    Timber.d("hseWebViewExitCondition(): cookie is empty, error")
                    authorizationState.onNext(ErrorResult())
                } else {
                    Timber.d("hseWebViewExitCondition(): cookie is not empty, authorization complete")
                    dataStore.setAuthorized(true)
                    dataStore.setSessionId(cookie.getSessionIdFromCookie())
                    authorizationState.onNext(SuccessResult())
                }
                true
            }

            else -> false
        }
    }

    private companion object {
        // Do this if there are multiple Rx chains in a viewModel
        val authChannel = Channel()

        const val DEFAULT_PROD_CLIENT_ID = "erp-miem"
    }
}
