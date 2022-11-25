package odoo.miem.android.common.network.authorization.impl.di

import dagger.Component
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationUseCaseApi

// TODO Description
/**
 * [AuthorizationUseCaseComponent] - **Dagger** компонент, который реализует интерфейс [IAuthorizationApi]
 * Провайдиться в общий **DI граф** через [AuthorizationUseCaseApiProvider].
 *
 * Подключаемые модули:
 *  - [AuthorizationUseCaseModule] - предоставляет [AuthorizationScreen] в *DI граф*
 *
 * @author Ворожцов Михаил
 */
@Component(
    modules = [
        AuthorizationUseCaseModule::class
    ]
)
interface AuthorizationUseCaseComponent : IAuthorizationUseCaseApi {
    companion object {
        fun create(): IAuthorizationUseCaseApi = DaggerAuthorizationUseCaseComponent.builder().build()
    }
}
