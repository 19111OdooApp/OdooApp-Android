package odoo.miem.android.common.network.authorization.impl

import io.reactivex.rxjava3.core.Observable
import odoo.miem.android.common.network.authorization.api.IAuthorizationUseCase
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationRepositoryApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.utils.Result

// TODO Description
class AuthorizationUseCase : IAuthorizationUseCase {

    private val authorizationRepository by api(IAuthorizationRepositoryApi::authorizationRepository)

    override fun generalAuthorization(baseUrl: String, login: String, password: String): Observable<Result> {
        // TODO Move from chain repo to result chain with data...
        authorizationRepository.generalAuthorization(
            baseUrl = baseUrl,
            login = login,
            password = password
        )
    }
}