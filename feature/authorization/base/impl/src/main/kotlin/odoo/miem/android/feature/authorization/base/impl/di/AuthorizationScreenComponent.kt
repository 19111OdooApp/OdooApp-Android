package odoo.miem.android.feature.authorization.base.impl.di

import dagger.Component
import odoo.miem.android.feature.authorization.base.api.di.IAuthorizationApi

/**
 * [AuthorizationScreenComponent] - **Dagger** component, which implements interface [IAuthorizationApi]
 * It is provided to the general **DI graph** through [AuthorizationScreenApiProvider].
 *
 * Connected modules:
 *  - [AuthorizationScreenModule] - provides [AuthorizationScreen] to the *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        AuthorizationScreenModule::class
    ],
)
interface AuthorizationScreenComponent : IAuthorizationApi {
    companion object {
        fun create(): IAuthorizationApi = DaggerAuthorizationScreenComponent.builder().build()
    }
}
