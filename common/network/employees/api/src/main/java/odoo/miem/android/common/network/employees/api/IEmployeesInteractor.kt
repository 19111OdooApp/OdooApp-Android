package odoo.miem.android.common.network.employees.api

import odoo.miem.android.common.network.employees.api.entities.EmployeeAvatarRequestHeaders
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

    fun getAllEmployeesInfo(paginationOffset: Int = 0): ResultSingle<List<EmployeeBasicInfo>>

    fun performEmployeesSearch(searchRequest: String): ResultSingle<List<EmployeeBasicInfo>>

    fun getAvatarRequestHeaders(): ResultSingle<List<EmployeeAvatarRequestHeaders>>

    fun getEmployeeDetails(employeeId: Long): ResultSingle<EmployeeDetails>
}
