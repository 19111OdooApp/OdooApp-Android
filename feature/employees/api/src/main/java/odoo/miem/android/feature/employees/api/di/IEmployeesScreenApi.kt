package odoo.miem.android.feature.employees.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.employees.api.IEmployeesScreen

/**
 * [IEmployeesScreenApi] needs for wrapping [IEmployeesScreen] for
 * subsequent submission to the DI graph
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IEmployeesScreenApi: Api {

    val employeesScreen: IEmployeesScreen
}