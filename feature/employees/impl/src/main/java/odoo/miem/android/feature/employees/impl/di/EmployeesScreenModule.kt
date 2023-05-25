package odoo.miem.android.feature.employees.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.feature.employees.api.IEmployeesScreen
import odoo.miem.android.feature.employees.impl.employeesScreen.EmployeesScreen

/**
 * [EmployeesScreenModule] - module for proving instance of [EmployeesScreen]
 * in general **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface EmployeesScreenModule {

    @Binds
    fun provideEmployeesScreen(impl: EmployeesScreen): IEmployeesScreen
}
