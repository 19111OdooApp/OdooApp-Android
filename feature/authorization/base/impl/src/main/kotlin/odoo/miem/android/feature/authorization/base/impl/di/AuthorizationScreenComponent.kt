package odoo.miem.android.feature.authorization.base.impl.di

import dagger.Component
import odoo.miem.android.feature.authorization.base.api.di.IAuthorizationApi

/**
 * [AuthorizationScreenComponent] - **Dagger** компонент, который реализует интерфейс [IAuthorizationApi]
 * Провайдиться в общий **DI граф** через [AuthorizationScreenApiProvider].
 *
 * Подключаемые модули:
 *  - [AuthorizationScreenModule] - предоставляет [AuthorizationScreen] в *DI граф*
 *
 * @author Ворожцов Михаил
 */
@Component(
    modules = [
        AuthorizationScreenModule::class
    ]
)
interface AuthorizationScreenComponent : IAuthorizationApi {
    companion object {
        fun create(): IAuthorizationApi = DaggerAuthorizationScreenComponent.builder().build()
    }
}