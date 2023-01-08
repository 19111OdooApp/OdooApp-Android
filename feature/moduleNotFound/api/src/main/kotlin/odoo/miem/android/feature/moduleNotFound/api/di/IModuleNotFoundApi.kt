package odoo.miem.android.feature.moduleNotFound.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.moduleNotFound.api.IModuleNotFoundScreen

/**
 * [IModuleNotFoundApi] needs for wrapping [IModuleNotFoundScreen] for
 * subsequent submission to the DI graph
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IModuleNotFoundApi : Api {

    val moduleNotFoundScreen: IModuleNotFoundScreen
}
