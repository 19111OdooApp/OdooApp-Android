package odoo.miem.android.platfom.dependecies

import android.content.Context

/**
 * Стандартная реализация PlatformDependencies для подстановки при инициализации
 * **Dagger** компонента.
 *
 * @param context который нужно доставить в остальные модули
 *
 * @author Ворожцов Михаил
 */
class DefaultPlatformDependencies(override val context: Context) : PlatformDependencies