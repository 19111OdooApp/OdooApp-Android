package odoo.miem.android.common.network.employees.impl.helpers

import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.core.networkApi.employees.api.source.EmployeeDetailsResponse
import timber.log.Timber

class EmployeeInteractorHelper {

    fun convertEmployeeInfoResponse(
        response: EmployeeDetailsResponse,
        avatarLink: String
    ): EmployeeDetails {
        Timber.d("convertEmployeeInfoResponse()")

        val department = response.departmentId.getSecondItem()

        val studyGroup = response.studyGroup.getSecondItem()

        val company = response.company.getSecondItem()

        val address = response.address.getSecondItem()

        val workLocation = response.workLocation.getSecondItem()

        val resourceCalendar = response.resourceCalendar.getSecondItem()

        val coach = response.coach.getSecondItem()

        val manager = response.manager.getSecondItem()

        val employeeDetails = EmployeeDetails(
            id = response.id,
            name = response.employeeName,
            avatarLink = avatarLink,
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
            employeeType = response.employeeType,
            timeZone = response.timezone
        )

        Timber.d("convertEmployeeInfoResponse(): result = $employeeDetails")
        return employeeDetails
    }

    private fun List<Any>?.getSecondItem(): String? {
        return this?.getOrNull(1) as? String
    }
}
