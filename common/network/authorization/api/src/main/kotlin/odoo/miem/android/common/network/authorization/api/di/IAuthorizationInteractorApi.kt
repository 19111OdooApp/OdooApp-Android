package odoo.miem.android.common.network.authorization.api.di

import odoo.miem.android.common.network.authorization.api.IAuthorizationInteractor
import odoo.miem.android.core.di.api.Api

/**
 * [IAuthorizationInteractorApi] needed for wrapping over [IAuthorizationInteractor] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IAuthorizationInteractorApi : Api {

    val authorizationInteractor: IAuthorizationInteractor
}
