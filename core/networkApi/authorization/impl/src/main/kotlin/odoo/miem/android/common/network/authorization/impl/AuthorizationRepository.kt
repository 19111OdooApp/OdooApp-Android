package odoo.miem.android.common.network.authorization.impl

import io.reactivex.rxjava3.core.Observable
import odoo.miem.android.common.network.authorization.api.IAuthorizationRepository
import odoo.miem.android.common.network.authorization.impl.source.IGeneralAuthorization
import odoo.miem.android.core.retrofitApiFabric.retrofitApi

// TODO Description
class AuthorizationRepository: IAuthorizationRepository {

    private val generalAuthorization by retrofitApi<IGeneralAuthorization>()

    override fun generalAuthorization(baseUrl: String, login: String, password: String): Observable<Int> {
        return generalAuthorization.authorization(
            // TODO create extension
        )
    }
}