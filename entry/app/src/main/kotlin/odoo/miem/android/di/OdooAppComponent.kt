package odoo.miem.android.di

import android.content.Context
import dagger.Component
import odoo.miem.android.platfom.dependecies.DefaultPlatformDependencies
import odoo.miem.android.platfom.dependecies.PlatformDependencies
import odoo.miem.android.platfom.di.PlatformApiProviderModule

/**
 * [OdooAppComponent] - **Dagger** компонент, который является родительским компонентом
 * для всех остальных компонентов и предоставляет ApiResolver с общей **Map** со всеми
 * **Api**
 *
 * Зависимости:
 *  - [PlatformDependencies] - запрос платформенных объектов в общий *DI граф*
 *
 * Подключаемые модули:
 *  - [PlatformApiProviderModule] - для доставки зависимости платформенных объектов
 *
 *
 * @author Ворожцов Михаил
 */
@Component(
    dependencies = [
        PlatformDependencies::class
    ],
    modules = [
        PlatformApiProviderModule::class,
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
