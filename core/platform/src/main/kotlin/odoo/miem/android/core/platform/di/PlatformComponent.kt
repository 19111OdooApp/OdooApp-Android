package odoo.miem.android.core.platform.di

import dagger.Component
import odoo.miem.android.core.platform.dependecies.PlatformDependencies

/**
 * [PlatformComponent] - **Dagger** компонент, реализующий интерфейс [PlatformApi] и
 * предоставляющий статический метод для создания экземпляра [PlatformApi].
 *
 * Провайдиться в общий **DI граф** через [PlatformApiProviderModule].
 *
 * Зависимости:
 *  - [PlatformDependencies] - платформенные объекты
 *
 * @author Ворожцов Михаил
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
