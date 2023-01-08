package odoo.miem.android.core.platform.di

import dagger.Component
import odoo.miem.android.core.platform.dependecies.PlatformDependencies

/**
 * [PlatformComponent] - **Dagger** component, which implements interface [PlatformApi] and
 * provides static method for creating an instance of [PlatformApi].
 *
 * It is provided to general **DI graph** through [PlatformApiProvider].
 *
 * Зависимости:
 *  - [PlatformDependencies] - platform objects
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    dependencies = [
        PlatformDependencies::class
    ]
)
interface PlatformComponent : PlatformApi {
    companion object {
        fun create(dependencies: PlatformDependencies): PlatformApi = DaggerPlatformComponent.builder()
            .platformDependencies(dependencies)
            .build()
    }
}
