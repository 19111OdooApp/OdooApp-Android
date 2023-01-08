package odoo.miem.android.core.platform.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.platform.dependecies.PlatformDependencies

/**
 * [PlatformApiProvider] - **Dagger** modules, which provides instances of platform objects
 * to general **DI graph**
 *
 * @param dependencies is needed to wrap the platform objects on which the component depends
 *
 * @author Vorozhtsov Mikhail
 */
@Module
class PlatformApiProvider {

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
