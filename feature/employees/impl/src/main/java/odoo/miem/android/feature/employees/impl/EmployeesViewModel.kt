package odoo.miem.android.feature.employees.impl

import androidx.compose.runtime.mutableStateListOf
import odoo.miem.android.common.network.employees.api.di.IEmployeesInteractorApi
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.common.network.selectingModules.api.di.ISelectingModulesInteractorApi
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.common.uiKitComponents.screen.base.searchLike.model.ScreenPage
import odoo.miem.android.common.utils.avatar.AvatarRequestHeader
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.di.impl.apiBlocking
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.di.RxApi
import odoo.miem.android.core.utils.rx.PresentationSchedulers
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.NothingResult
import odoo.miem.android.core.utils.state.ResultSubject
import odoo.miem.android.core.utils.state.SuccessResult
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
    val avatarRequestHeaders: ResultSubject<List<AvatarRequestHeader>> by lazyEmptyResultPublishSubject()

    val allEmployeesState: ResultSubject<ScreenPage<EmployeeBasicInfo>> by lazyEmptyResultPublishSubject()
    val employeesList = mutableStateListOf<EmployeeBasicInfo>()

    val employeesSearchState: ResultSubject<List<EmployeeBasicInfo>> by lazyEmptyResultPublishSubject()
    val filteredEmployeesList = mutableStateListOf<EmployeeBasicInfo>()

    var employeesMaxSize: Int? = null
        private set

    var employeesPageSize: Int? = null
        private set

    var isSessionExpired = false
        private set

    var employeesCurrentPage: Int = 0
        private set

    fun onEmployeesOpen() {
        employeesList.clear()

        getAllEmployees()
        getUserInfo()
        getAvatarRequestHeaders()
    }

    fun onEmployeeDetailsOpen(employeeId: Long) {
        getAvatarRequestHeaders()
        getEmployeeDetails(employeeId)
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

    fun getAllEmployees(currentPage: Int = 0) {
        Timber.d("getAllEmployees()")

        employeesCurrentPage = currentPage

        if (employeesList.isNotEmpty() && employeesMaxSize != null && employeesPageSize != null) {

            val employeesPage = getEmployeesPage(currentPage)

            allEmployeesState.onNext(SuccessResult(employeesPage))
        } else {
            employeesInteracor
                .getAllEmployeesInfo(paginationOffset = employeesList.size)
                .schedule(
                    allEmployeesChannel,
                    onSuccess = { result ->
                        Timber.d("getAllEmployees(): result = $result")

                        result.data?.let {
                            employeesList.addAll(it.employees)
                            employeesPageSize = it.batchSize

                            it.maxSize?.let { size ->
                                employeesMaxSize = size
                            }
                        }

                        Timber.d("EMPLOYEES SIZE ${employeesList.size} PAGE SIZE $employeesPageSize MAX SIZE $employeesMaxSize")

                        val employeesPage = getEmployeesPage(currentPage)

                        when (result) {
                            is ErrorResult -> {
                                isSessionExpired = result.isSessionExpired

                                allEmployeesState.onNext(ErrorResult())
                            }
                            is SuccessResult -> {
                                allEmployeesState.onNext(SuccessResult(employeesPage))
                            }
                            is NothingResult -> {
                                allEmployeesState.onNext(NothingResult())
                            }
                            else -> {}
                        }
                    },
                    onError = {
                        Timber.e(it)
                    }
                )
        }
    }

    private fun getEmployeesPage(currentPage: Int): ScreenPage<EmployeeBasicInfo> {
        return if (employeesMaxSize != null && employeesPageSize != null) {

            val leftBorder = employeesPageSize!! * currentPage
            val rightBorder = employeesPageSize!! * (currentPage + 1)

            val fromIndex = if (leftBorder in employeesList.indices) {
                leftBorder
            } else {
                0
            }

            val toIndex = if (rightBorder <= employeesList.size) {
                rightBorder
            } else {
                val difference = rightBorder - employeesList.size
                rightBorder - difference
            }

            Timber.d("FROM INDEX $fromIndex TO INDEX $toIndex")

            ScreenPage(
                maxSize = employeesMaxSize!!,
                currentPage = currentPage,
                pageSize = employeesPageSize!!,
                fromIndex = fromIndex,
                toIndex = toIndex,
                items = employeesList.subList(fromIndex, toIndex)
            )
        } else {
            ScreenPage(
                maxSize = employeesList.size,
                currentPage = currentPage,
                pageSize = employeesList.size,
                fromIndex = 0,
                toIndex = employeesList.size,
                items = employeesList
            )
        }
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
            .getEmployeeAvatarRequestHeaders()
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
