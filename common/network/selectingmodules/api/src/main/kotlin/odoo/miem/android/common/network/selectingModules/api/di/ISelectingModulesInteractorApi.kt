package odoo.miem.android.common.network.selectingModules.api.di

import odoo.miem.android.common.network.selectingModules.api.ISelectingModulesInteractor
import odoo.miem.android.core.di.api.Api

/**
 * [ISelectingModulesInteractorApi] needed for wrapping over [ISelectingModulesInteractor] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface ISelectingModulesInteractorApi : Api {

    val selectingModulesInteractor: ISelectingModulesInteractor
}
