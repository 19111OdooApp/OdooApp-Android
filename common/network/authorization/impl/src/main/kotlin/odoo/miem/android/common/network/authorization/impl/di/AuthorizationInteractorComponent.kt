package odoo.miem.android.common.network.authorization.impl.di

import dagger.Component
import odoo.miem.android.common.network.authorization.api.di.IAuthorizationInteractorApi

// TODO Description
/**
 * [AuthorizationInteractorComponent] - **Dagger** компонент, который реализует интерфейс [IAuthorizationApi]
 * Провайдиться в общий **DI граф** через [AuthorizationInteractorApiProvider].
 *
 * Подключаемые модули:
 *  - [AuthorizationInteractorModule] - предоставляет [AuthorizationScreen] в *DI граф*
 *
 * @author Ворожцов Михаил
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
