package odoo.miem.android.platfom.dependecies

import android.content.Context

/**
 * [PlatformDependencies] - интерфейс для **Dagger** компонентов, которым нужны
 * платформенные объекты
 *
 * @see DefaultPlatformDependencies
 *
 * @author Ворожцов Михаил
 */
interface PlatformDependencies {

    val context: Context
}