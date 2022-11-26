package odoo.miem.android.common.network.authorization.api.di

import odoo.miem.android.common.network.authorization.api.IAuthorizationInteractor
import odoo.miem.android.core.di.api.Api

// TODO Description
interface IAuthorizationInteractorApi : Api {

    val authorizationInteractor: IAuthorizationInteractor
}