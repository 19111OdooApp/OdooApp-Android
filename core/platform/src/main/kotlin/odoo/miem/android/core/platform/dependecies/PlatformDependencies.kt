package odoo.miem.android.core.platform.dependecies

import android.content.Context

/**
 * [PlatformDependencies] - interface for **Dagger** components, that need platform objects
 *
 * @see DefaultPlatformDependencies
 *
 * @author Vorozhtsov Mikhail
 */
interface PlatformDependencies {

    val context: Context
}
