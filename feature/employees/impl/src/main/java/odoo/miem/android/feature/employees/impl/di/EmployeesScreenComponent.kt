package odoo.miem.android.feature.employees.impl.di

import dagger.Component
import odoo.miem.android.feature.employees.api.di.IEmployeesScreenApi

/**
 * [EmployeesScreenComponent] - **Dagger** component, which implements [IEmployeesScreenApi]
 * Providing in general **DI graph** with a help of [EmployeesScreenApiProvider].
 *
 * Included modules:
 *  - [EmployeesScreenModule] - provides [EmployeesScreen]
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        EmployeesScreenModule::class
    ]
)
interface EmployeesScreenComponent: IEmployeesScreenApi {

    companion object {
        fun create(): IEmployeesScreenApi = DaggerEmployeesScreenComponent.builder().build()
    }
}