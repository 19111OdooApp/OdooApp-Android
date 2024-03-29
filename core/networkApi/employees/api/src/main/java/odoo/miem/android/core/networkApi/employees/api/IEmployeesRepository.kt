package odoo.miem.android.core.networkApi.employees.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.employees.api.source.AllEmployeesResponse
import odoo.miem.android.core.networkApi.employees.api.source.EmployeeDetailsResponse

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
    fun getAllEmployees(paginationOffset: Int = 0, limit: Int = 30): Single<AllEmployeesResponse>

    /**
     * [performEmployeeSearch] - function for performing search on backend and get result via
     * Single<[AllEmployeesResponse]>
     *
     * @return Single<[AllEmployeesResponse]> which provides found employees
     */
    fun performEmployeesSearch(searchRequest: String): Single<AllEmployeesResponse>

    /**
     * [getEmployeeDetailInfo] - function for requesting detail info about specific employee
     *
     * @return Observable<Boolean> - true or false whether updating Odoo database was successful
     */
    fun getEmployeeDetailInfo(employeeId: Long): Single<List<EmployeeDetailsResponse>>
}
