package odoo.miem.android.feature.authorization.base.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen

/**
 * [IAuthorizationApi] нужен для обертки [IAuthorizationScreen] для последующего
 * предоставления в di граф
 *
 * @see Api
 *
 * @author Ворожцов Михаил
 */
interface IAuthorizationApi: Api {

    val authorizationScreen: IAuthorizationScreen
}