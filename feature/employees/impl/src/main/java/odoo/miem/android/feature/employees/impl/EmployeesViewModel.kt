package odoo.miem.android.feature.employees.impl

import androidx.compose.runtime.mutableStateListOf
import odoo.miem.android.common.network.employees.api.di.IEmployeesInteractorApi
import odoo.miem.android.common.network.employees.api.entities.EmployeeAvatarRequestHeaders
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.common.network.selectingModules.api.di.ISelectingModulesInteractorApi
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.di.impl.apiBlocking
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.di.RxApi
import odoo.miem.android.core.utils.rx.PresentationSchedulers
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.ResultSubject
import timber.log.Timber

/**
 * [EmployeesViewModel] handle major logic for [EmployeesScreen]
 *
 * @author Egor Danilov
 */
class EmployeesViewModel(
    schedulers: PresentationSchedulers = apiBlocking(RxApi::presentationSchedulers)
) : BaseViewModel(schedulers) {

    private val selectingModulesInteractor by api(ISelectingModulesInteractorApi::selectingModulesInteractor)
    private val employeesInteracor by api(IEmployeesInteractorApi::employeesInteractor)

    val userInfoState: ResultSubject<User> by lazyEmptyResultPublishSubject()
    val employeeDetails: ResultSubject<EmployeeDetails> by lazyEmptyResultPublishSubject()
    val avatarRequestHeaders: ResultSubject<List<EmployeeAvatarRequestHeaders>> by lazyEmptyResultPublishSubject()

    val allEmployeesState: ResultSubject<List<EmployeeBasicInfo>> by lazyEmptyResultPublishSubject()
    val allEmployeesList = mutableStateListOf<EmployeeBasicInfo>()

    val employeesSearchState: ResultSubject<List<EmployeeBasicInfo>> by lazyEmptyResultPublishSubject()
    val filteredEmployeesList = mutableStateListOf<EmployeeBasicInfo>()

    var isSessionExpired = false
        private set

    fun onEmployeesOpen() {
        allEmployeesList.clear()

        getAllEmployees()
        getUserInfo()
        getAvatarRequestHeaders()
    }

    private fun getUserInfo() {
        Timber.d("getUserInfo()")

        userInfoState.onLoadingState()
        selectingModulesInteractor
            .getUserInfo()
            .schedule(
                userInfoChannel,
                onSuccess = { result ->
                    Timber.d("getUserInfo(): result = $result")
                    userInfoState.onNext(result)
                },
                onError = Timber::e
            )
    }

    fun getAllEmployees() {
        Timber.d("getAllEmployees()")

        employeesInteracor
            .getAllEmployeesInfo(paginationOffset = allEmployeesList.size)
            .schedule(
                allEmployeesChannel,
                onSuccess = { result ->
                    Timber.d("getAllEmployees(): result = $result")

                    result.data?.let {
                        allEmployeesList.addAll(it)
                    }
                    if (result is ErrorResult) {
                        isSessionExpired = result.isSessionExpired
                    }

                    allEmployeesState.onNext(result)
                },
                onError = {
                    Timber.e(it)
                }
            )
    }

    fun performEmployeeSearch(searchRequest: String) {
        Timber.d("performEmployeeSearch(): search request = $searchRequest")

        employeesSearchState.onLoadingState()
        employeesInteracor
            .performEmployeesSearch(searchRequest)
            .schedule(
                employeeSearchChannel,
                onSuccess = { result ->
                    Timber.d("performEmployeeSearch(): result = $result")

                    result.data?.let {
                        filteredEmployeesList.clear()
                        filteredEmployeesList.addAll(it)
                    }
                    employeesSearchState.onNext(result)
                },
                onError = Timber::e
            )
    }

    fun getEmployeeDetails(employeeId: Long) {
        Timber.d("getEmployeeDetails()")

        employeeDetails.onLoadingState()
        employeesInteracor
            .getEmployeeDetails(employeeId = employeeId)
            .schedule(
                employeesDetailsChannel,
                onSuccess = { result ->
                    Timber.d("getEmployeeDetails(): result = $result")

                    employeeDetails.onNext(result)
                },
                onError = Timber::e
            )
    }

    private fun getAvatarRequestHeaders() {
        Timber.d("getAvatarRequestHeaders()")

        avatarRequestHeaders.onLoadingState()
        employeesInteracor
            .getAvatarRequestHeaders()
            .schedule(
                avatarRequestHeadersChannel,
                onSuccess = { result ->
                    Timber.d("getAvatarRequestHeaders(): result = $result")

                    avatarRequestHeaders.onNext(result)
                },
                onError = Timber::e
            )
    }

    private companion object {
        // Do this if there are multiple Rx chains in a viewModel
        val userInfoChannel = Channel()
        val allEmployeesChannel = Channel()
        val employeesDetailsChannel = Channel()
        val avatarRequestHeadersChannel = Channel()
        val employeeSearchChannel = Channel()
    }
}
