package odoo.miem.android.core.networkApi.authorization.api

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
     * @return Observable<Int> which provides UID of user
     */
    fun generalAuthorization(login: String, password: String): Single<Int>
}
