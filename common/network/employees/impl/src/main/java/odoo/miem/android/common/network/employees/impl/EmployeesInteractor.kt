package odoo.miem.android.common.network.employees.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.common.network.employees.api.IEmployeesInteractor
import odoo.miem.android.common.network.employees.api.entities.AllEmployeesBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.common.network.employees.impl.helpers.EmployeeInteractorHelper
import odoo.miem.android.common.utils.avatar.AvatarRequestHeader
import odoo.miem.android.common.utils.avatar.getAvatarLink
import odoo.miem.android.common.utils.avatar.getAvatarRequestHeaders
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.employees.api.di.IEmployeesRepositoryApi
import odoo.miem.android.core.networkApi.employees.api.source.AllEmployeesResponse
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
    private val dataStore by api(IDataStoreApi::dataStore)

    private val helper = EmployeeInteractorHelper()

    override fun getAllEmployeesInfo(paginationOffset: Int): ResultSingle<AllEmployeesBasicInfo> {
        Timber.d("getAllEmployeesInfo(): pagination offset = $paginationOffset")

        return employeesRepository.getAllEmployees(paginationOffset, DEFAULT_BATCH_SIZE)
            .map<Result<AllEmployeesBasicInfo>> { response ->
                Timber.d("getAllEmployeesInfo(): response = $response")

                val employeesInfo = AllEmployeesBasicInfo(
                    maxSize = response.length,
                    batchSize = DEFAULT_BATCH_SIZE,
                    employees = response.toListDTO()
                )

                Timber.d("getAllEmployeesInfo(): result = $employeesInfo")

                SuccessResult(employeesInfo)
            }
            .onErrorReturn {
                Timber.e("getAllEmployeesInfo(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun getEmployeeAvatarRequestHeaders(): ResultSingle<List<AvatarRequestHeader>> {
        return Single.just(
            SuccessResult(
                getAvatarRequestHeaders(sessionId = dataStore.sessionId)
            )
        )
    }

    override fun performEmployeesSearch(searchRequest: String): ResultSingle<List<EmployeeBasicInfo>> {
        Timber.d("performEmployeesSearch(): search request = $searchRequest")

        return employeesRepository.performEmployeesSearch(searchRequest)
            .map<Result<List<EmployeeBasicInfo>>> { response ->
                Timber.d("performEmployeesSearch(): response = $response")

                SuccessResult(response.toListDTO())
            }
            .onErrorReturn {
                Timber.e("performEmployeesSearch(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun getEmployeeDetails(employeeId: Long): ResultSingle<EmployeeDetails> {
        Timber.d("getEmployeeDetails")

        return employeesRepository.getEmployeeDetailInfo(employeeId = employeeId)
            .map<Result<EmployeeDetails>> { response ->
                val employeeDetails = helper.convertEmployeeInfoResponse(
                    response = response.first(),
                    avatarLink = getAvatarLink(userId = employeeId, odooUrl = dataStore.url)
                )

                Timber.d("getEmployeeDetails(): result = $employeeDetails")

                SuccessResult(employeeDetails)
            }
            .onErrorReturn {
                Timber.e("getEmployeeDetails(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    private fun AllEmployeesResponse.toListDTO(): List<EmployeeBasicInfo> =
        records?.fold(mutableListOf()) { list, employee ->
            employee.id?.let {
                list.add(
                    EmployeeBasicInfo(
                        id = it,
                        name = employee.employeeName ?: DEFAULT_EMPLOYEE_NAME,
                        job = employee.job,
                        email = employee.email,
                        phone = employee.phone,
                        avatarLink = getAvatarLink(userId =  it, odooUrl = dataStore.url),
                    )
                )
            }
            list
        } ?: emptyList()

    companion object {
        const val DEFAULT_EMPLOYEE_NAME = "Unknown Employee"

        const val DEFAULT_BATCH_SIZE = 30
    }
}
