package odoo.miem.android.common.network.employees.impl.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.common.network.employees.api.IEmployeesInteractor
import odoo.miem.android.common.network.employees.impl.EmployeesInteractor

/**
 * [EmployeesInteractorModule] - module for providing
 * instance of [EmployeesInteractor] in **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface EmployeesInteractorModule {

    @Binds
    fun provideEmployeesInteractor(impl: EmployeesInteractor): IEmployeesInteractor
}