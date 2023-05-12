package odoo.miem.android.core.networkApi.employees.api.di

import dagger.Binds
import dagger.Module
import odoo.miem.android.core.networkApi.employees.api.EmployeesRepository
import odoo.miem.android.core.networkApi.employees.api.IEmployeesRepository

/**
 * [EmployeesRepositoryModule] - module for providing instance of [EmployeesRepository]
 * in **DI graph**
 *
 * @author Egor Danilov
 */
@Module
interface EmployeesRepositoryModule {

    @Binds
    fun provideEmployeesRepository(impl: EmployeesRepository): IEmployeesRepository
}
