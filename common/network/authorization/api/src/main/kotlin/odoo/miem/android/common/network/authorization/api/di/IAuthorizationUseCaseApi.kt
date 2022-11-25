package odoo.miem.android.common.network.authorization.api.di

import odoo.miem.android.common.network.authorization.api.IAuthorizationUseCase
import odoo.miem.android.core.di.api.Api

// TODO Description
interface IAuthorizationUseCaseApi : Api {

    val authorizationUseCase: IAuthorizationUseCase
}