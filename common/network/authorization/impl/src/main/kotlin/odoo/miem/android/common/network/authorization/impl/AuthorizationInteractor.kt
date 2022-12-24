package odoo.miem.android.common.network.authorization.impl

import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.common.network.authorization.api.IAuthorizationInteractor
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationRepositoryApi
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultObservable
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import javax.inject.Inject

/**
 * [AuthorizationInteractor] - implementation of [IAuthorizationInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class AuthorizationInteractor @Inject constructor() : IAuthorizationInteractor {

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
                ErrorResult(R.string.general_authorization_error)
            }
    }
}
