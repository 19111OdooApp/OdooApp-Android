package odoo.miem.android.common.network.authorization.api

import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [IAuthorizationInteractor] - interface for wrapping authorization
 * use cases, for instance - general authorization
 *
 * @author Vorozhtos Mikhail
 */
interface IAuthorizationInteractor {

    /**
     * [generalAuthorization] - base authorization with a help of Odoo XMLRPC
     *
     * @param baseUrl is baseUrl of Odoo server
     * @param login of user
     * @param password of user
     *
     * @return Observable<Result> which we can observer and get Success or Error result
     */
    fun generalAuthorization(baseUrl: String, login: String, password: String): ResultSingle<Unit>

    fun getUserInfo(): ResultSingle<Unit>
}
