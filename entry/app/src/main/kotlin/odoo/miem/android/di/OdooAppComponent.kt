package odoo.miem.android.di

import android.content.Context
import dagger.Component
import odoo.miem.android.common.network.authorization.impl.di.AuthorizationRepositoryApiProvider
import odoo.miem.android.common.network.authorization.impl.di.AuthorizationInteractorApiProvider
import odoo.miem.android.core.dataStore.impl.di.DataStoreApiProvider
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.di.impl.ApiResolver
import odoo.miem.android.core.platform.dependecies.DefaultPlatformDependencies
import odoo.miem.android.core.platform.dependecies.PlatformDependencies
import odoo.miem.android.core.platform.di.PlatformApiProvider
import odoo.miem.android.feature.authorization.base.impl.di.AuthorizationScreenApiProvider

/**
 * [OdooAppComponent] - **Dagger** компонент, который является родительским компонентом
 * для всех остальных компонентов и предоставляет ApiResolver с общей **Map** со всеми
 * **Api**
 *
 * Зависимости:
 *  - [PlatformDependencies] - запрос платформенных объектов в общий *DI граф*
 *
 * Подключаемые модули:
 *  - TODO AuthorizationRepositoryApiProvider
 *  - TODO AuthorizationScreenApiProvider
 *  - TODO AuthorizationUseCaseApiProvider
 *  - TODO DataStoreApiProvider
 *  - [PlatformApiProvider] - для доставки зависимости платформенных объектов
 *
 *
 * @author Ворожцов Михаил
 */
@Component(
    dependencies = [
        PlatformDependencies::class
    ],
    modules = [
        AuthorizationRepositoryApiProvider::class,
        AuthorizationScreenApiProvider::class,
        AuthorizationInteractorApiProvider::class,
        DataStoreApiProvider::class,
        PlatformApiProvider::class
    ]
)
interface OdooAppComponent {

    val apiResolver: ApiResolver
}

/**
 * Инициализация **DI графа** в application
 *
 * @param context нужен для инициализации [PlatformDependencies]
 */
fun Context.initApis(context: Context): OdooAppComponent = DaggerOdooAppComponent
    .builder()
    .platformDependencies(DefaultPlatformDependencies(context))
    .build()
    .also {
        ApiRegistry.init(it.apiResolver)
    }
