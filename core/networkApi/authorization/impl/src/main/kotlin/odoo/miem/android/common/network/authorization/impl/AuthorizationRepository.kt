package odoo.miem.android.common.network.authorization.impl

import io.reactivex.rxjava3.core.Observable
import odoo.miem.android.common.network.authorization.api.IAuthorizationRepository
import odoo.miem.android.common.network.authorization.impl.source.IGeneralAuthorization
import odoo.miem.android.core.retrofitApiFabric.retrofitApi
import odoo.miem.android.core.utils.network.RequestHelpers
import timber.log.Timber
import javax.inject.Inject

/**
 * [AuthorizationRepository] - implementation of [IAuthorizationRepository]
 *
 * @author Vorozhtsov Mikhail
 */
class AuthorizationRepository @Inject constructor() : IAuthorizationRepository {

    private val generalAuthorization by retrofitApi<IGeneralAuthorization>()

    override fun generalAuthorization(login: String, password: String): Observable<Int> {
        Timber.d("generalAuthorization(): login = $login, password = $password")

        return generalAuthorization.authorization(
            RequestHelpers.authorizationRequestBody(
                login = login,
                password = password
            )
        )
    }
}
