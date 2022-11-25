package odoo.miem.android.common.network.authorization.api

import io.reactivex.rxjava3.core.Observable

// TODO Description
interface IAuthorizationRepository {

    fun generalAuthorization(baseUrl: String, login: String, password: String): Observable<Int>
}