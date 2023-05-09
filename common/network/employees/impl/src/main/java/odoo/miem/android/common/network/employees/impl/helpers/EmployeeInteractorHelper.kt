package odoo.miem.android.common.network.employees.impl.helpers

import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.core.networkApi.employees.api.source.AllEmployeesResponse
import odoo.miem.android.core.networkApi.employees.api.source.EmployeeInfoResponse
import timber.log.Timber

class EmployeeInteractorHelper {

    fun convertAllEmployeesResponse(
        response: AllEmployeesResponse
    ): List<EmployeeBasicInfo> {
        Timber.d("convertAllEmployeesResponse()")

        val employees = response.records.map { info ->
            val job = info.job as? String
            val email = info.email as? String
            val phone = info.phone as? String

            EmployeeBasicInfo(
                id = info.id,
                name = info.employeeName,
                job = job,
                email = email,
                phone = phone,
                avatar = info.avatar
            )
        }

        Timber.d("convertAllEmployeesResponse(): result = $employees")
        return employees
    }

    fun convertEmployeeInfoResponse(
        response: EmployeeInfoResponse
    ): EmployeeDetails {
        Timber.d("convertEmployeeInfoResponse()")

        val job = response.job as? String
        val mobilePhone = response.mobilePhone as? String
        val workPhone = response.workPhone as? String
        val email = response.email as? String
        val aboutMe = response.aboutMe as? String
        val employeeType = response.employeeType as? String

        val department = (response.departmentId as? List<*>)
            ?.getOrNull(1)
            ?.toString()

        val studyGroup = (response.studyGroup as? List<*>)
            ?.getOrNull(1)
            ?.toString()

        val company = (response.company as? List<*>)
            ?.getOrNull(1)
            ?.toString()

        val address = (response.address as? List<*>)
            ?.getOrNull(1)
            ?.toString()

        val workLocation = (response.workLocation as? List<*>)
            ?.getOrNull(1)
            ?.toString()

        val resourceCalendar = (response.resourceCalendar as? List<*>)
            ?.getOrNull(1)
            ?.toString()

        val coach = (response.coach as? List<*>)
            ?.getOrNull(1)
            ?.toString()

        val manager = (response.manager as? List<*>)
            ?.getOrNull(1)
            ?.toString()

        val employeeDetails = EmployeeDetails(
            id = response.id,
            name = response.employeeName,
            job = job,
            mobilePhone = mobilePhone,
            workPhone = workPhone,
            email = email,
            department = department,
            studyGroup = studyGroup,
            company = company,
            address = address,
            workLocation = workLocation,
            resourceCalendar = resourceCalendar,
            aboutMe = aboutMe,
            coach = coach,
            manager = manager,
            employeeType = employeeType
        )

        Timber.d("convertEmployeeInfoResponse(): result = $employeeDetails")
        return employeeDetails
    }
}
