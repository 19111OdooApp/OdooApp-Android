package odoo.miem.android.feature.employees.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.feature.employees.api.di.IEmployeesScreenApi

/**
 * [EmployeesScreenApiProvider] - **Dagger** module for providing
 * [EmployeesScreenComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class EmployeesScreenApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IEmployeesScreenApi::class)
    fun provideEmployeesScreenApiProvider() = ApiProvider {
        EmployeesScreenComponent.create()
    }
}