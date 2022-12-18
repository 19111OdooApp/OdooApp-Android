package odoo.miem.android.feature.selectingModules.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen

/**
 * [ISelectingModulesApi] needs for wrapping [ISelectingModulesScreen] for
 * subsequent submission to the DI graph
 *
 * @see Api
 *
 * @author Vorozhtsov Mikhail
 */
interface ISelectingModulesApi : Api {

    val selectingModulesScreen: ISelectingModulesScreen
}
