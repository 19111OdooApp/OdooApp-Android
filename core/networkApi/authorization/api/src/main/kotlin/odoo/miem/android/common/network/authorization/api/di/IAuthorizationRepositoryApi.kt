package odoo.miem.android.common.network.authorization.api.di

import odoo.miem.android.common.network.authorization.api.IAuthorizationRepository
import odoo.miem.android.core.di.api.Api

// TODO Description
/**
 * [IAuthorizationRepositoryApi] нужен для обертки [IAuthorizationScreen] для последующего
 * предоставления в di граф
 *
 * @see Api
 *
 * @author Ворожцов Михаил
 */
interface IAuthorizationRepositoryApi : Api {

    val authorizationRepository: IAuthorizationRepository
}
