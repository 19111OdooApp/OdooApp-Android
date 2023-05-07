package odoo.miem.android.core.networkApi.employees.api

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.employees.api.source.AllEmployeesResponse
import odoo.miem.android.core.networkApi.employees.api.source.EmployeeInfoResponse
import odoo.miem.android.core.networkApi.employees.api.source.GetEmployeeInfoRequest
import odoo.miem.android.core.networkApi.employees.api.source.IEmployeesInfo
import timber.log.Timber
import javax.inject.Inject

/**
 * [EmployeesRepository] - implementation of [IEmployeesRepository]
 *
 * @author Egor Danilov
 */
class EmployeesRepository @Inject constructor() : IEmployeesRepository {

    private val employeesInfo by jsonRpcApi<IEmployeesInfo>()

    override fun getAllEmployees(): Single<AllEmployeesResponse> {
        Timber.d("getAllEmployees()")

        return Single.fromCallable {
            employeesInfo.getAllEmployees()
        }.subscribeOn(Schedulers.io())
    }

    override fun getEmployeeInfo(employeeId: Int): Single<List<EmployeeInfoResponse>> {
        Timber.d("getEmployeeInfo: id = $employeeId")

        val request = GetEmployeeInfoRequest(
            args = listOf(
                employeeId,
                getEmployeeInfoFields
            )
        )

        return Single.fromCallable {
            employeesInfo.getEmployeeInfo(args = request.args)
        }.subscribeOn(Schedulers.io())
    }

    private companion object {

        val getEmployeeInfoFields = listOf(
            "id", "name", "job_title", "mobile_phone", "work_phone", "work_email",
            "department_id", "studygroup_id", "company_id", "address_id",
            "work_location_id", "resource_calendar_id", "cv"
        )
    }
}
