package odoo.miem.android.common.network.authorization.impl

import io.reactivex.rxjava3.core.Observable
import odoo.miem.android.common.network.authorization.api.IAuthorizationInteractor
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationRepositoryApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.utils.ErrorResult
import odoo.miem.android.core.utils.Result
import odoo.miem.android.core.utils.SuccessResult
import timber.log.Timber

// TODO Description
class AuthorizationInteractor : IAuthorizationInteractor {

    private val authorizationRepository by api(IAuthorizationRepositoryApi::authorizationRepository)

    override fun generalAuthorization(baseUrl: String, login: String, password: String): Observable<Result> {
        Timber.d("generalAuthorization(): baseUrl = $baseUrl, login = $login, password = $password")
        // TODO Save baseUrl in datastore
        return authorizationRepository.generalAuthorization(
            baseUrl = baseUrl,
            login = login,
            password = password
        )
            .map<Result> {
                // TODO Save uid in datastore
                Timber.d("generalAuthorization(): uid = $it")
                SuccessResult(data = it)
            }
            .onErrorReturn {
                Timber.e("generalAuthorization(): error message = ${it.message}")
                ErrorResult(it.message)
            }
    }
}