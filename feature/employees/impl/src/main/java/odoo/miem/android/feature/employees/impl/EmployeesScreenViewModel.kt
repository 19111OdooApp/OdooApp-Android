package odoo.miem.android.feature.employees.impl

import odoo.miem.android.common.network.employees.api.di.IEmployeesInteractorApi
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.di.impl.apiBlocking
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.di.RxApi
import odoo.miem.android.core.utils.rx.PresentationSchedulers
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ResultSubject
import timber.log.Timber

/**
 * [EmployeesScreenViewModel] handle major logic for [EmployeesScreen]
 *
 * @author Egor Danilov
 */
class EmployeesScreenViewModel(
    schedulers: PresentationSchedulers = apiBlocking(RxApi::presentationSchedulers)
): BaseViewModel(schedulers) {

    private val employeesInteracor by api(IEmployeesInteractorApi::employeesInteractor)

    val allEmployeesState: ResultSubject<List<EmployeeBasicInfo>> by lazyEmptyResultPublishSubject()
    val employeeDetails: ResultSubject<EmployeeDetails> by lazyEmptyResultPublishSubject()

    fun getAllEmployees() {
        Timber.d("getAllEmployees()")

        allEmployeesState.onLoadingState()
        employeesInteracor
            .getAllEmployeesInfo()
            .schedule(
                allEmployeesChannel,
                onSuccess = { result ->
                    Timber.d("getAllEmployees(): result = $result")

                    allEmployeesState.onNext(result)
                },
                onError = Timber::e
            )
    }

    fun getEmployeeDetails(employeeId: Int) {
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

    private companion object {
        // Do this if there are multiple Rx chains in a viewModel
        val allEmployeesChannel = Channel()
        val employeesDetailsChannel = Channel()
    }
}