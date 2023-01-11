package odoo.miem.android.core.platform.dependecies

import android.content.Context

/**
 * Default implementation of PlatformDependencies for substitution at initialization of
 * **Dagger** component.
 *
 * @param context to be delivered to other modules
 *
 * @author Vorozhtsov Mikhail
 */
class DefaultPlatformDependencies(override val context: Context) : PlatformDependencies
