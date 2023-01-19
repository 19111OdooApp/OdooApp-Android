package odoo.miem.android.core.networkApi.selectingModules.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkApi.selectingModules.api.ISelectingModulesRepository

/**
 * [ISelectingModulesRepositoryApi] needed for wrapping over [ISelectingModulesRepository] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface ISelectingModulesRepositoryApi : Api {

    val selectingModulesRepository: ISelectingModulesRepository
}