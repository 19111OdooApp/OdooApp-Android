package odoo.miem.android.feature.authorization.base.impl

import odoo.miem.android.common.network.authorization.api.di.IAuthorizationInteractorApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.di.impl.apiBlocking
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.di.RxApi
import odoo.miem.android.core.utils.rx.PresentationSchedulers
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ResultSubject
import timber.log.Timber

/**
 * [AuthorizationViewModel] handle major logic for [AuthorizationScreen]
 *
 * @author Vorozhtsov Mikhail, Alexander Lyutikov
 */
class AuthorizationViewModel(
    schedulers: PresentationSchedulers = apiBlocking(RxApi::presentationSchedulers)
) : BaseViewModel(schedulers) {

    private val authorizationInteractor by api(IAuthorizationInteractorApi::authorizationInteractor)

    val authorizationState: ResultSubject<Int> by lazyEmptyResultPublishSubject()

    fun generalAuthorization(baseUrl: String, login: String, password: String) {
        Timber.d("generalAuthorization(): baseUrl = $baseUrl, login = $login, password = $password")

        authorizationState.onLoadingState()
        authorizationInteractor
            .generalAuthorization(
                baseUrl = baseUrl,
                login = login,
                password = password
            ).schedule(
                authChannel,
                onNext = {
                    Timber.d("generalAuthorization(): result = $it")

                    authorizationState.onNext(it)
                },
                onError = Timber::e
            )
    }

    companion object {
        // Do this if there are multiple Rx chains in a viewModel
        private val authChannel = Channel()
    }
}
