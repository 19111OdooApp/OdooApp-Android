package odoo.miem.android.feature.employees.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.employees.api.IEmployeeDetailsScreen
import odoo.miem.android.feature.employees.api.IEmployeesScreen
import odoo.miem.android.feature.employees.impl.employeesScreen.allEmployees.EmployeesScreen
import odoo.miem.android.feature.employees.impl.employeesScreen.employeeDetails.EmployeeDetailsScreen

/**
 * [EmployeesModule] - module for proving instance of [EmployeesScreen]
 * in general **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface EmployeesModule {

    @Binds
    fun provideEmployeesScreen(impl: EmployeesScreen): IEmployeesScreen

    @Binds
    fun provideEmployeeDetailsScreen(impl: EmployeeDetailsScreen): IEmployeeDetailsScreen
}
