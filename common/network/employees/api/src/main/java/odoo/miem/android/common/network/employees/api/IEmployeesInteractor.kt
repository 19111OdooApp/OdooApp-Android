package odoo.miem.android.common.network.employees.api

import odoo.miem.android.common.network.employees.api.entities.AllEmployeesBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.common.utils.avatar.AvatarRequestHeader
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [IEmployeesInteractor] - interface for wrapping employees module
 * use cases
 *
 * @author Egor Danilov
 */
interface IEmployeesInteractor {

    fun getAllEmployeesInfo(paginationOffset: Int = 0): ResultSingle<AllEmployeesBasicInfo>

    fun performEmployeesSearch(searchRequest: String): ResultSingle<List<EmployeeBasicInfo>>

    fun getEmployeeAvatarRequestHeaders(): ResultSingle<List<AvatarRequestHeader>>

    fun getEmployeeDetails(employeeId: Long): ResultSingle<EmployeeDetails>
}
