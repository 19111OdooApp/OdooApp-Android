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
            val job = if (info.job is String) {
                info.job.toString()
            } else {
                null
            }

            val email = if (info.email is String) {
                info.email.toString()
            } else {
                null
            }

            val phone = if (info.phone is String) {
                info.phone.toString()
            } else {
                null
            }

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

        val job = if (response.job is String) {
            response.job.toString()
        } else {
            null
        }

        val mobilePhone = if (response.mobilePhone is String) {
            response.mobilePhone.toString()
        } else {
            null
        }

        val workPhone = if (response.workPhone is String) {
            response.workPhone.toString()
        } else {
            null
        }

        val email = if (response.email is String) {
            response.email.toString()
        } else {
            null
        }

        val department = response.departmentId
        val castedDepartment = if (department is List<*>) {
            department[1].toString()
        } else {
            null
        }

        val studyGroup = response.studyGroup
        val castedStudyGroup = if (studyGroup is List<*>) {
            studyGroup[1].toString()
        } else {
            null
        }

        val company = response.company
        val castedCompany = if (company is List<*>) {
            company[1].toString()
        } else {
            null
        }

        val address = response.address
        val castedAddress = if (address is List<*>) {
            address[1].toString()
        } else {
            null
        }

        val workLocation = response.workLocation
        val castedWorkLocation = if (workLocation is List<*>) {
            workLocation[1].toString()
        } else {
            null
        }

        val resourceCalendar = response.resourceCalendar
        val castedResourceCalendar = if (resourceCalendar is List<*>) {
            resourceCalendar[1].toString()
        } else {
            null
        }

        val aboutMe = if (response.aboutMe is String) {
            response.aboutMe.toString()
        } else {
            null
        }

        val coach = response.coach
        val castedCoach = if (coach is List<*>) {
            coach[1].toString()
        } else {
            null
        }

        val manager = response.manager
        val castedManager = if (manager is List<*>) {
            manager[1].toString()
        } else {
            null
        }

        val employeeType = if (response.employeeType is String) {
            response.employeeType.toString()
        } else {
            null
        }

        val employeeDetails = EmployeeDetails(
            id = response.id,
            name = response.employeeName,
            job = job,
            mobilePhone = mobilePhone,
            workPhone = workPhone,
            email = email,
            department = castedDepartment,
            studyGroup = castedStudyGroup,
            company = castedCompany,
            address = castedAddress,
            workLocation = castedWorkLocation,
            resourceCalendar = castedResourceCalendar,
            aboutMe = aboutMe,
            coach = castedCoach,
            manager = castedManager,
            employeeType = employeeType
        )

        Timber.d("convertEmployeeInfoResponse(): result = $employeeDetails")
        return employeeDetails
    }
}
