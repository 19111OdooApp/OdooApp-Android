package odoo.miem.android.common.network.authorization.impl.di

import dagger.Component
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationInteractorApi
import odoo.miem.android.common.network.authorization.impl.AuthorizationInteractor

/**
 * [AuthorizationInteractorComponent] - **Dagger** component, which implements interface [IAuthorizationInteractorApi]
 * Providing in general **DI graph** with a help of [AuthorizationInteractorApiProvider].
 *
 * Included modules:
 *  - [AuthorizationInteractorModule] - provide [AuthorizationInteractor] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        AuthorizationInteractorModule::class
    ]
)
interface AuthorizationInteractorComponent : IAuthorizationInteractorApi {
    companion object {
        fun create(): IAuthorizationInteractorApi = DaggerAuthorizationInteractorComponent.builder().build()
    }
}
