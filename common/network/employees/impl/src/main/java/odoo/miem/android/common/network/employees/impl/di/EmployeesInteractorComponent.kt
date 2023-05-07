package odoo.miem.android.common.network.employees.impl.di

import dagger.Component
import odoo.miem.android.common.network.employees.api.di.IEmployeesInteractorApi

/**
 * [EmployeesInteractorComponent] - **Dagger** component, which implements interface [IEmployeesInteractorApi]
 * Providing in general **DI graph** with a help of [EmployeesInteractorApiProvider].
 *
 * Included modules:
 *  - [EmployeesInteractorModule] - provides [EmployeesInteractor] in *DI graph*
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        EmployeesInteractorModule::class
    ]
)
interface EmployeesInteractorComponent: IEmployeesInteractorApi {

    companion object {
        fun create(): IEmployeesInteractorApi = DaggerEmployeesInteractorComponent.builder()
            .build()
    }
}