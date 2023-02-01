package odoo.miem.android.core.networkApi.authorization.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.authorization.api.IAuthorizationRepository
import odoo.miem.android.core.networkApi.authorization.api.source.UserInfoResponse
import odoo.miem.android.core.networkApi.authorization.impl.source.IGeneralAuthorization
import odoo.miem.android.core.utils.network.RequestHelpers
import timber.log.Timber
import javax.inject.Inject

/**
 * [AuthorizationRepository] - implementation of [IAuthorizationRepository]
 *
 * @author Vorozhtsov Mikhail
 */
class AuthorizationRepository @Inject constructor() : IAuthorizationRepository {

    private val generalAuthorization by jsonRpcApi<IGeneralAuthorization>()

    override fun generalAuthorization(login: String, password: String): Single<String> {
        Timber.d("generalAuthorization(): login = $login, password = $password")

        return Single.fromCallable {
            generalAuthorization.authorization(
                database = RequestHelpers.databaseName,
                login = login,
                password = password
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun getUserInfo(): Single<UserInfoResponse> = Single.fromCallable {
        generalAuthorization.userInfo()
    }
}
