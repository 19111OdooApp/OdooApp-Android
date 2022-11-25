package odoo.miem.android.common.network.authorization.impl

import io.reactivex.rxjava3.core.Completable
import odoo.miem.android.common.network.authorization.api.IAuthorizationRepository
import odoo.miem.android.core.network.authorization.IGeneralAuthorization
import odoo.miem.android.core.retrofitApiFabric.getRetrofitApi
import odoo.miem.android.core.retrofitApiFabric.retrofitApi

class AuthorizationRepository: IAuthorizationRepository {

    private val generalAuthorization by retrofitApi<IGeneralAuthorization>()

    override fun generalAuthorization(baseUrl: String, login: String, password: String): Completable {
        // TODO
    }
}