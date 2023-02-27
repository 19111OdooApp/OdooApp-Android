package odoo.miem.android.core.networkApi.authorization.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkApi.authorization.api.IAuthorizationRepository

/**
 * [IAuthorizationRepositoryApi] needed for wrapping over [IAuthorizationRepository] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface IAuthorizationRepositoryApi : Api {

    val authorizationRepository: IAuthorizationRepository
}
