package odoo.miem.android.feature.employees.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.employees.api.di.IEmployeesApi

/**
 * [EmployeesApiProvider] - **Dagger** module for providing
 * [EmployeesComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class EmployeesApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IEmployeesApi::class)
    fun provideEmployeesScreenApiProvider() = ApiProvider {
        EmployeesComponent.create()
    }
}
