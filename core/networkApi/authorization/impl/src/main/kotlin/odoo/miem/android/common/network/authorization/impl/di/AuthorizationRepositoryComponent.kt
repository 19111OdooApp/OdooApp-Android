package odoo.miem.android.common.network.authorization.impl.di

import dagger.Component
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationRepositoryApi

// TODO Description
/**
 * [AuthorizationRepositoryComponent] - **Dagger** компонент, который реализует интерфейс [IAuthorizationApi]
 * Провайдиться в общий **DI граф** через [AuthorizationRepositoryApiProvider].
 *
 * Подключаемые модули:
 *  - [AuthorizationRepositoryModule] - предоставляет [AuthorizationScreen] в *DI граф*
 *
 * @author Ворожцов Михаил
 */
@Component(
    modules = [
        AuthorizationRepositoryModule::class
    ]
)
interface AuthorizationRepositoryComponent : IAuthorizationRepositoryApi {
    companion object {
        fun create(): IAuthorizationRepositoryApi = DaggerAuthorizationRepositoryComponent.builder().build()
    }
}
