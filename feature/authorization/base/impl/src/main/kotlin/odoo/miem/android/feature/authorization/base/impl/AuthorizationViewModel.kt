package odoo.miem.android.feature.authorization.base.impl

import odoo.miem.android.common.network.authorization.api.di.IAuthorizationInteractorApi
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.builder.HseUriAuthorizationBuilder
import odoo.miem.android.core.utils.builder.urlProcessing
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
    ): String = HseUriAuthorizationBuilder.Builder()
        .setBaseDomain(rawDomain)
        .build()
        .generateHseAuthorizationUrl()

    fun hseWebViewExitCondition(rawUrl: String, currentUrl: String?, cookie: String?): Boolean {
        // TODO Make new package in utils for regex only
        // TODO Bug with Вы уже вошли
        // TODO Loading state
        // TODO Optimize jsonrpc for it
        // TODO Close webview, floating button?
        return when {
            currentUrl == null -> false
            currentUrl.startsWith(urlProcessing(rawUrl)) -> {
                if (cookie == null) {
                    Timber.d("hseWebViewExitCondition(): cookie is empty, error")
                    authorizationState.onNext(ErrorResult())
                } else {
                    Timber.d("hseWebViewExitCondition(): cookie not empty, authorization complete")
                    dataStore.setHseAuthorized(true)
                    dataStore.setSessionId(cookie) // TODO get from cookie session_id
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
    }
}
