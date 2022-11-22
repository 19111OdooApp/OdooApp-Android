package odoo.miem.android.common.network.authorization.impl

import io.reactivex.rxjava3.core.Completable
import odoo.miem.android.common.network.authorization.api.IAuthorizationRepository

class AuthorizationRepository: IAuthorizationRepository {

    override fun generalAuthorization(baseUrl: String, login: String, password: String): Completable {
        // TODO
    }
}