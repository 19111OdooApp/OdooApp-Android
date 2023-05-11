package odoo.miem.android.common.network.employees.impl.helpers

import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.core.networkApi.employees.api.source.EmployeeDetailsResponse
import timber.log.Timber

class EmployeeInteractorHelper {

    fun convertEmployeeInfoResponse(
        response: EmployeeDetailsResponse
    ): EmployeeDetails {
        Timber.d("convertEmployeeInfoResponse()")

        val department = response.departmentId
            ?.getOrNull(1)
            ?.toString()

        val studyGroup = response.studyGroup
            ?.getOrNull(1)
            ?.toString()

        val company = response.company
            ?.getOrNull(1)
            ?.toString()

        val address = response.address
            ?.getOrNull(1)
            ?.toString()

        val workLocation = response.workLocation
            ?.getOrNull(1)
            ?.toString()

        val resourceCalendar = response.resourceCalendar
            ?.getOrNull(1)
            ?.toString()

        val coach = response.coach
            ?.getOrNull(1)
            ?.toString()

        val manager = response.manager
            ?.getOrNull(1)
            ?.toString()

        val employeeDetails = EmployeeDetails(
            id = response.id,
            name = response.employeeName,
            job = response.job,
            mobilePhone = response.mobilePhone,
            workPhone = response.workPhone,
            email = response.email,
            department = department,
            studyGroup = studyGroup,
            company = company,
            address = address,
            workLocation = workLocation,
            resourceCalendar = resourceCalendar,
            aboutMe = response.aboutMe,
            coach = coach,
            manager = manager,
            employeeType = response.employeeType
        )

        Timber.d("convertEmployeeInfoResponse(): result = $employeeDetails")
        return employeeDetails
    }
}
