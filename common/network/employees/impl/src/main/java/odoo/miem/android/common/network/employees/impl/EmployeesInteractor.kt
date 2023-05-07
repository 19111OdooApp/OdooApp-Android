package odoo.miem.android.common.network.employees.impl

import odoo.miem.android.common.network.employees.api.IEmployeesInteractor
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.common.network.employees.impl.helpers.EmployeeInteractorHelper
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.employees.api.di.IEmployeesRepositoryApi
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import javax.inject.Inject

/**
 * [EmployeesInteractor] - implementation of [IEmployeesInteractor]
 *
 * @author Egor Danilov
 */
class EmployeesInteractor @Inject constructor() : IEmployeesInteractor {

    private val employeesRepository by api(IEmployeesRepositoryApi::employeesRepository)

    private val helper = EmployeeInteractorHelper()

    override fun getAllEmployeesInfo(): ResultSingle<List<EmployeeBasicInfo>> {
        Timber.d("getAllEmployeesInfo()")

        return employeesRepository.getAllEmployees()
            .map<Result<List<EmployeeBasicInfo>>> { response ->
                val employeesInfo = helper.convertAllEmployeesResponse(response)

                SuccessResult(employeesInfo)
            }
            .onErrorReturn {
                Timber.e("getAllEmployeesInfo(): error message = ${it.message}")
                ErrorResult(R.string.employees_error)
            }
    }

    override fun getEmployeeDetails(employeeId: Int): ResultSingle<EmployeeDetails> {
        Timber.d("getEmployeeDetails")

        return employeesRepository.getEmployeeInfo(employeeId = employeeId)
            .map<Result<EmployeeDetails>> { response ->
                val employeeDetails = helper.convertEmployeeInfoResponse(response[0])

                SuccessResult(employeeDetails)
            }
            .onErrorReturn {
                Timber.e("getEmployeeDetails(): error message = ${it.message}")
                ErrorResult(R.string.employees_error)
            }
    }
}
