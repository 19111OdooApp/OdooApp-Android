package odoo.miem.android.common.network.authorization.impl

import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.common.network.authorization.api.IAuthorizationInteractor
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationRepositoryApi
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.utils.ErrorResult
import odoo.miem.android.core.utils.Result
import odoo.miem.android.core.utils.ResultObservable
import odoo.miem.android.core.utils.SuccessResult
import timber.log.Timber

/**
 * [AuthorizationInteractor] - implementation of [IAuthorizationInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class AuthorizationInteractor : IAuthorizationInteractor {

    private val authorizationRepository by api(IAuthorizationRepositoryApi::authorizationRepository)
    private val dataStore by api(IDataStoreApi::dataStore)

    override fun generalAuthorization(baseUrl: String, login: String, password: String): ResultObservable<Int> {
        Timber.d("generalAuthorization(): baseUrl = $baseUrl, login = $login, password = $password")

        dataStore.setUrl(baseUrl)

        return authorizationRepository.generalAuthorization(
            login = login,
            password = password
        )
            .subscribeOn(Schedulers.io())
            .map<Result<Int>> {
                Timber.d("generalAuthorization(): uid = $it")
                dataStore.setUID(it)
                SuccessResult(data = it)
            }
            .onErrorReturn {
                Timber.e("generalAuthorization(): error message = ${it.message}")
                ErrorResult(it.message)
            }
    }
}
