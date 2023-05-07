package odoo.miem.android.core.networkApi.employees.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.employees.api.source.AllEmployeesResponse
import odoo.miem.android.core.networkApi.employees.api.source.EmployeeInfoResponse

/**
 * [IEmployeesRepository] - interface for wrapping data layer
 * logic, which is connected with employees module
 *
 * @author Egor Danilov
 */
interface IEmployeesRepository {

    /**
     * [getAllEmployees] - function for requesting basic info about all employees

     * @return Single<[AllEmployeesResponse]> which provides all employees basic info
     */
    fun getAllEmployees(): Single<AllEmployeesResponse>

    /**
     * [getEmployeeInfo] - function for requesting detail info about specific employee
     *
     * @return Observable<Boolean> - true or false whether updating Odoo database was successful
     */
    fun getEmployeeInfo(employeeId: Int): Single<List<EmployeeInfoResponse>>
}