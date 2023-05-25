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
    private val employeesList = mutableStateListOf<EmployeeBasicInfo>()

    val employeesSearchState: ResultSubject<List<EmployeeBasicInfo>> by lazyEmptyResultPublishSubject()
    val filteredEmployeesList = mutableStateListOf<EmployeeBasicInfo>()

    private var employeesMaxSize: Int? = null
    private var employeesPageSize: Int? = null
    private var employeesCurrentPage: Int = 0

    var isSessionExpired = false
        private set

    fun onEmployeesOpen() {
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

    fun getAllEmployees(newPage: Int? = null) {
        Timber.d("getAllEmployees()")

        newPage?.let {
            employeesCurrentPage = it
        }

        val existingEmployeesPage = getEmployeesPage(employeesCurrentPage)

        if (existingEmployeesPage != null) {
            allEmployeesState.onNext(SuccessResult(existingEmployeesPage))
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

                        val employeesPage = getEmployeesPage(employeesCurrentPage)

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

    private fun getEmployeesPage(currentPage: Int): ScreenPage<EmployeeBasicInfo>? {
        return employeesPageSize?.let { pageSize ->
            val leftBorder = pageSize * currentPage
            val rightBorder = pageSize * (currentPage + 1)

            employeesMaxSize?.let { maxSize ->

                val fromIndex = if (leftBorder in 0..employeesList.size) {
                    leftBorder
                } else {
                    0
                }

                val toIndex = if (rightBorder > employeesList.size && rightBorder <= maxSize) {
                    null
                } else if (rightBorder <= employeesList.size) {
                    rightBorder
                } else {
                    val difference = rightBorder - maxSize
                    rightBorder - difference
                }

                toIndex
                    ?.takeIf { it <= employeesList.size }
                    ?.let {
                        ScreenPage(
                            maxSize = maxSize,
                            currentPage = currentPage,
                            pageSize = pageSize,
                            fromIndex = fromIndex,
                            toIndex = toIndex,
                            items = employeesList.subList(fromIndex, toIndex)
                        )
                    }
            }
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
