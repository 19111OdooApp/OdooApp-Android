package odoo.miem.android.core.platform.di

import android.content.Context
import odoo.miem.android.core.di.api.Api

/**
 * [PlatformApi] is intended for wrapping platform objects for providing interface to components of
 * **Dagger 2**
 *
 * @property context is an instance of an abstract class [Context]
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface PlatformApi : Api {

    val context: Context
}
