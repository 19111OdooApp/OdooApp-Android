package odoo.miem.android.core.networkApi.userInfo.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkApi.userInfo.api.IUserModulesRepository

/**
 * [IUserModulesRepositoryApi] needed for wrapping over [IUserModulesRepository] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IUserModulesRepositoryApi : Api {

    val selectingModulesRepository: IUserModulesRepository
}
