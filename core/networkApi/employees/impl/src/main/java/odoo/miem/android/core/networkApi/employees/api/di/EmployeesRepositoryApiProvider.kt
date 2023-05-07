package odoo.miem.android.core.networkApi.employees.api.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import odoo.miem.android.core.di.impl.ApiKey
import odoo.miem.android.core.di.impl.ApiProvider

/**
 * [EmployeesRepositoryApiProvider] - **Dagger** module for providing
 * [EmployeesRepositoryComponent] in general map
 *
 * @author Egor Danilov
 */
@Module
class EmployeesRepositoryApiProvider {

    @Provides
    @IntoMap
    @ApiKey(IEmployeesRepositoryApi::class)
    fun provideUserInfoRepositoryApiProvider() = ApiProvider {
        EmployeesRepositoryComponent.create()
    }
}
