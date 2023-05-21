package odoo.miem.android.common.network.employees.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.common.network.employees.api.IEmployeesInteractor
import odoo.miem.android.common.network.employees.api.entities.EmployeeAvatarRequestHeaders
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.common.network.employees.impl.helpers.EmployeeInteractorHelper
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

    override fun getAllEmployeesInfo(paginationOffset: Int): ResultSingle<List<EmployeeBasicInfo>> {
        Timber.d("getAllEmployeesInfo(): pagination offset = $paginationOffset")

        return employeesRepository.getAllEmployees(paginationOffset)
            .map<Result<List<EmployeeBasicInfo>>> { response ->
                Timber.d("getAllEmployeesInfo(): response = $response")

                SuccessResult(response.toListDTO())
            }
            .onErrorReturn {
                Timber.e("getAllEmployeesInfo(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun getAvatarRequestHeaders(): ResultSingle<List<EmployeeAvatarRequestHeaders>> {
        return Single.just(
            SuccessResult(
                listOf(
                    EmployeeAvatarRequestHeaders(
                        name = COOKIE_REQUEST_HEADER,
                        value = COOKIE_SESSION_ID + dataStore.sessionId
                    )
                )
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
                val avatarLink = getAvatarLink(employeeId)

                val employeeDetails = helper
                    .convertEmployeeInfoResponse(response.first())
                    .copy(avatarLink = avatarLink)

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
                        avatarLink = getAvatarLink(it),
                    )
                )
            }
            list
        } ?: emptyList()

    private fun getAvatarLink(employeeId: Long): String {
        val url = dataStore.url

        return "${url}web/image?model=hr.employee.public&id=$employeeId&field=avatar_128"
    }

    companion object {
        const val DEFAULT_EMPLOYEE_NAME = "Unknown Employee"

        const val COOKIE_REQUEST_HEADER = "cookie"
        const val COOKIE_SESSION_ID = "session_id="
    }
}
