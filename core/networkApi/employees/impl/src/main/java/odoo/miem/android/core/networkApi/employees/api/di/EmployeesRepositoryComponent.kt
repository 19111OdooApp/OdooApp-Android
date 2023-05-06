package odoo.miem.android.core.networkApi.employees.api.di

import dagger.Component

/**
 * [EmployeesRepositoryComponent] - **Dagger** component, which implements interface [IEmployeesRepositoryApi]
 * Providing in general **DI graph** with a help of [EmployeesRepositoryApiProvider].
 *
 * Included modules:
 *  - [EmployeesRepositoryModule] - provides [EmployeesRepository] in *DI graph*
 *
 * @author Egor Danilov
 */
@Component(
    modules = [
        EmployeesRepositoryModule::class
    ]
)
interface EmployeesRepositoryComponent: IEmployeesRepositoryApi {

    companion object {
        fun create(): IEmployeesRepositoryApi = DaggerEmployeesRepositoryComponent.builder()
            .build()
    }
}