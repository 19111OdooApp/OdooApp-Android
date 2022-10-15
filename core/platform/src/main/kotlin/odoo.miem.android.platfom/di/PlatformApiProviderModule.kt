package odoo.miem.android.platfom.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.di.ApiKey
import odoo.miem.android.di.ApiProvider
import odoo.miem.android.platfom.dependecies.PlatformDependencies

/**
 * [PlatformApiProviderModule] - **Dagger** модуль, который предоставляет
 * инстанс платформенных объектов в общий **DI граф**
 *
 * @param dependencies нужно для обертки платформенных объектов, от которых зависит компонент
 *
 * @author Ворожцов Михаил
 */
@Module
class PlatformApiProviderModule {

    @Provides
    @IntoMap
    @ApiKey(PlatformApi::class)
    fun providesLoggerFactoryApiProvider(
        dependencies: PlatformDependencies
    ) = ApiProvider {
        PlatformComponent.create(
            dependencies = dependencies
        )
    }
}