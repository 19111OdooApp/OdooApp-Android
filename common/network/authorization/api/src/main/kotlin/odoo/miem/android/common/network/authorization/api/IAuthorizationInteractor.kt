package odoo.miem.android.common.network.authorization.api

import io.reactivex.rxjava3.core.Observable
import odoo.miem.android.core.utils.Result

interface IAuthorizationInteractor {

    fun generalAuthorization(baseUrl: String, login: String, password: String): Observable<Result>
}