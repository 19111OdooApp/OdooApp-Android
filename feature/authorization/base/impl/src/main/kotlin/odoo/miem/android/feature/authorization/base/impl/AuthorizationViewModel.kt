package odoo.miem.android.feature.authorization.base.impl

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationInteractorApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.ResultSubject
import odoo.miem.android.core.utils.rx.PresentationSchedulers
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import timber.log.Timber
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    schedulers: PresentationSchedulers
) : BaseViewModel(schedulers) {

    private val authorizationInteractor by api(IAuthorizationInteractorApi::authorizationInteractor)

    val authorizationState: ResultSubject<Int> by lazyEmptyResultPublishSubject()

    fun generalAuthorization(baseUrl: String, login: String, password: String) {
        Timber.d("generalAuthorization(): baseUrl = $baseUrl, login = $login, password = $password")

        authorizationInteractor
            .generalAuthorization(
                baseUrl = baseUrl,
                login = login,
                password = password
            ).schedule(
                authChannel,
                onNext = {
                    Timber.d("generalAuthorization(): result = $it")
                    // TODO Is loading?
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
