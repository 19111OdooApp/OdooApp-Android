package odoo.miem.android.feature.authorization.base.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationInteractorApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.utils.Result
import odoo.miem.android.core.utils.processing
import timber.log.Timber

class AuthorizationViewModel : ViewModel() {

    private val authorizationInteractor by api(IAuthorizationInteractorApi::authorizationInteractor)
    private val compositeDisposable by lazy { CompositeDisposable() }

    // TODO Move from live data?
    val authorizationState = MutableLiveData<Result>()

    fun generalAuthorization(baseUrl: String, login: String, password: String) {
        Timber.d("generalAuthorization(): baseUrl = $baseUrl, login = $login, password = $password")

        val observable = authorizationInteractor
            .generalAuthorization(
                baseUrl = baseUrl,
                login = login,
                password = password
            )
            .processing()
            .subscribe {
                Timber.d("generalAuthorization(): result = $it")
                authorizationState.postValue(it)
            }

        compositeDisposable.add(observable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}