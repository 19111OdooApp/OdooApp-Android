package odoo.miem.android.common.network.authorization.api

import io.reactivex.rxjava3.core.Observable

interface IAuthorizationUseCase {

    fun generalAuthorization(baseUrl: String, login: String, password: String): Observable<Int>
}