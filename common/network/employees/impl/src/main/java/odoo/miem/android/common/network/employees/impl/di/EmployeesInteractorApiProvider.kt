package odoo.miem.android.common.network.employees.impl.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.common.network.employees.api.di.IEmployeesInteractorApi
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

/**
 * [EmployeesInteractorApiProvider] - **Dagger** module for providing
 * [EmployeesInteractorComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class EmployeesInteractorApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IEmployeesInteractorApi::class)
    fun provideEmployeesInteractorApiProvider() = ApiProvider {
        EmployeesInteractorComponent.create()
    }
}