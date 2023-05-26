package odoo.miem.android.feature.employees.impl.di

import dagger.Component
import odoo.miem.android.feature.employees.api.di.IEmployeesApi

/**
 * [EmployeesComponent] - **Dagger** component, which implements [IEmployeesApi]
 * Providing in general **DI graph** with a help of [EmployeesApiProvider].
 *
 * Included modules:
 *  - [EmployeesModule] - provides [EmployeesScreen]
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        EmployeesModule::class
    ]
)
interface EmployeesComponent : IEmployeesApi {

    companion object {
        fun create(): IEmployeesApi = DaggerEmployeesComponent.builder().build()
    }
}
