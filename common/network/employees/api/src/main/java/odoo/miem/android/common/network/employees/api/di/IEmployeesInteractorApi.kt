package odoo.miem.android.common.network.employees.api.di

import odoo.miem.android.common.network.employees.api.IEmployeesInteractor
import odoo.miem.android.core.di.api.Api

/**
 * [IEmployeesInteractorApi] needed for wrapping over [IEmployeesInteractor] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IEmployeesInteractorApi: Api {

    val employeesInteractor: IEmployeesInteractor
}