package odoo.miem.android.common.network.authorization.impl

import odoo.miem.android.common.network.authorization.api.IAuthorizationInteractor
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.authorization.api.di.IAuthorizationRepositoryApi
import odoo.miem.android.core.utils.builder.urlProcessing
import odoo.miem.android.core.utils.regex.getSessionIdFromCookie
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
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

    override fun generalAuthorization(
        baseUrl: String,
        login: String,
        password: String
    ): ResultSingle<Unit> {
        Timber.d("generalAuthorization(): baseUrl = $baseUrl, login = $login, password = $password")

        dataStore.setUrl(urlProcessing(baseUrl))

        return authorizationRepository.generalAuthorization(
            login = login,
            password = password
        )
            .map<Result<Unit>> { cookie ->
                Timber.d("generalAuthorization(): sessionId = $cookie")
                dataStore.setAuthorized(true)
                dataStore.setSessionId(
                    cookie.split(COOKIE_SPLIT_SIGN)
                        .find { it.contains(FIELD_SESSION_ID) }
                        .orEmpty()
                        .getSessionIdFromCookie()
                )
                SuccessResult()
            }
            .onErrorReturn {
                Timber.e("generalAuthorization(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    private companion object {
        const val COOKIE_SPLIT_SIGN = ";"
        const val FIELD_SESSION_ID = "session_id"
    }
}
