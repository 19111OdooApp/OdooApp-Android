package odoo.miem.android.common.network.employees.impl

import odoo.miem.android.common.network.employees.api.IEmployeesInteractor
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.employees.api.di.IEmployeesRepositoryApi
import odoo.miem.android.core.utils.state.ResultSingle
import javax.inject.Inject

/**
 * [EmployeesInteractor] - implementation of [IEmployeesInteractor]
 *
 * @author Egor Danilov
 */
class EmployeesInteractor @Inject constructor(): IEmployeesInteractor {

    private val employeesRepository by api(IEmployeesRepositoryApi::employeesRepository)

    override fun getAllEmployeesInfo(): ResultSingle<List<EmployeeBasicInfo>> {
        TODO("Not yet implemented")
    }

    override fun getEmployeesDetails(employeedId: Int): ResultSingle<EmployeeDetails> {
        TODO("Not yet implemented")
    }
}