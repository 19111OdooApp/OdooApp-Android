package odoo.miem.android.common.network.authorization.api.di

import odoo.miem.android.common.network.authorization.api.IAuthorizationRepository
import odoo.miem.android.core.di.api.Api

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
