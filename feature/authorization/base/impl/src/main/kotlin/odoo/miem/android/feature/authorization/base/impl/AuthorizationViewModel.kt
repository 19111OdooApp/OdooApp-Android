package odoo.miem.android.feature.authorization.base.impl

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationInteractorApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import timber.log.Timber

class AuthorizationViewModel : ViewModel() {

    private val authorizationInteractor by api(IAuthorizationInteractorApi::authorizationInteractor)
    private val compositeDisposable by lazy { CompositeDisposable() }

    val authorizationState by lazyEmptyResultPublishSubject()

    fun generalAuthorization(baseUrl: String, login: String, password: String) {
        Timber.d("generalAuthorization(): baseUrl = $baseUrl, login = $login, password = $password")

        val observable = authorizationInteractor
            .generalAuthorization(
                baseUrl = baseUrl,
                login = login,
                password = password
            )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Timber.d("generalAuthorization(): result = $it")
                authorizationState.onNext(it)
            }

        compositeDisposable.add(observable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}