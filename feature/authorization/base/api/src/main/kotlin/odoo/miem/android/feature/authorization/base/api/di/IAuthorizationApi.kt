package odoo.miem.android.feature.authorization.base.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen

/**
 * [IAuthorizationApi] is needed for wrapping  [IAuthorizationScreen] for providing it to DI graph
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IAuthorizationApi : Api {

    val authorizationScreen: IAuthorizationScreen
}
