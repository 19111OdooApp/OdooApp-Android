package odoo.miem.android.core.networkApi.authorization.impl.di

import dagger.Component
import odoo.miem.android.core.networkApi.authorization.api.di.IAuthorizationRepositoryApi
import odoo.miem.android.core.networkApi.authorization.impl.AuthorizationRepository

/**
 * [AuthorizationRepositoryComponent] - **Dagger** component, which implements interface [IAuthorizationRepositoryApi]
 * Providing in general **DI graph** with a help of [AuthorizationRepositoryApiProvider].
 *
 * Included modules:
 *  - [AuthorizationRepositoryModule] - provide [AuthorizationRepository] in *DI graph*
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    modules = [
        AuthorizationRepositoryModule::class
    ]
)
interface AuthorizationRepositoryComponent : IAuthorizationRepositoryApi {
    companion object {
        fun create(): IAuthorizationRepositoryApi = DaggerAuthorizationRepositoryComponent.builder()
            .build()
    }
}
