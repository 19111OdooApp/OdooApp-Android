package odoo.miem.android.di

import android.content.Context
import dagger.Component
import odoo.miem.android.common.network.authorization.impl.di.AuthorizationInteractorApiProvider
import odoo.miem.android.common.network.authorization.impl.di.AuthorizationRepositoryApiProvider
import odoo.miem.android.core.dataStore.impl.di.DataStoreApiProvider
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.di.impl.ApiResolver
import odoo.miem.android.core.platform.dependecies.DefaultPlatformDependencies
import odoo.miem.android.core.platform.dependecies.PlatformDependencies
import odoo.miem.android.core.platform.di.PlatformApiProvider
import odoo.miem.android.core.utils.di.RxApiProvider
import odoo.miem.android.feature.authorization.base.impl.di.AuthorizationScreenApiProvider
import odoo.miem.android.feature.moduleNotFound.impl.di.ModuleNotFoundScreenApiProvider
import odoo.miem.android.feature.selectingModules.impl.di.SelectingModulesScreenApiProvider

/**
 * [OdooAppComponent] - **Dagger** component, which is the parent component
 * for all  other components and provides [ApiResolver] with a common **Map** with all
 * **Api**
 *
 * Dependencies:
 *  - [PlatformDependencies] - request platform objects to general *DI graphg*
 *
 * Connected modules:
 *  - TODO AuthorizationRepositoryApiProvider
 *  - TODO AuthorizationScreenApiProvider
 *  - TODO AuthorizationUseCaseApiProvider
 *  - TODO DataStoreApiProvider
 *  - [PlatformApiProvider] - to deliver the dependency of platform objects
 *  - TODO SelectingModulesScreenApiProvider
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    dependencies = [
        PlatformDependencies::class,
    ],
    modules = [
        AuthorizationRepositoryApiProvider::class,
        AuthorizationScreenApiProvider::class,
        AuthorizationInteractorApiProvider::class,
        DataStoreApiProvider::class,
        PlatformApiProvider::class,
        RxApiProvider::class,
        SelectingModulesScreenApiProvider::class,
        ModuleNotFoundScreenApiProvider::class,
    ]
)
interface OdooAppComponent {

    val apiResolver: ApiResolver
}

/**
 * Initialization of application's **DI graph**
 *
 * @param context is needed for initialization of [PlatformDependencies]
 */
fun Context.initApis(
    context: Context,
): OdooAppComponent = DaggerOdooAppComponent
    .builder()
    .platformDependencies(DefaultPlatformDependencies(context))
    .build()
    .also {
        ApiRegistry.init(it.apiResolver)
    }
