package odoo.miem.android.common.network.employees.api

import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [IEmployeesInteractor] - interface for wrapping employees module
 * use cases
 *
 * @author Egor Danilov
 */
interface IEmployeesInteractor {

    fun getAllEmployeesInfo(): ResultSingle<List<EmployeeBasicInfo>>

    fun getEmployeesDetails(employeeId: Int): ResultSingle<EmployeeDetails>
}