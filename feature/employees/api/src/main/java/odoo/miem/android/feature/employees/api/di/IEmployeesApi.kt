package odoo.miem.android.feature.employees.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.feature.employees.api.IEmployeeDetailsScreen
import odoo.miem.android.feature.employees.api.IEmployeesScreen

/**
 * [IEmployeesApi] needs for wrapping [IEmployeesScreen] for
 * subsequent submission to the DI graph
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IEmployeesApi : Api {

    val employeesScreen: IEmployeesScreen

    val employeeDetailsScreen: IEmployeeDetailsScreen
}
