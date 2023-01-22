package odoo.miem.android.common.network.authorization.api

import io.reactivex.rxjava3.core.Single

/**
 * [IAuthorizationRepository] - interface for wrapping data layer
 * logic, which is connected with authorization
 *
 * @author Vorozhtos Mikhail
 */
interface IAuthorizationRepository {

    /**
     * [generalAuthorization] - function for requesting general authorization of Odoo
     *
     * @param login of user
     * @param password of user
     *
     * @return Observable<String> which provides cookie of session
     */
    fun generalAuthorization(login: String, password: String): Single<String>
}
