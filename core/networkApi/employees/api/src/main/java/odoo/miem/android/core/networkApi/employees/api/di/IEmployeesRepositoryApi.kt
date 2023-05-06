package odoo.miem.android.core.networkApi.employees.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkApi.employees.api.IEmployeesRepository

/**
 * [IEmployeesRepositoryApi] needed for wrapping over [IEmployeesRepository] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IEmployeesRepositoryApi : Api {

    val employeesRepository: IEmployeesRepository
}