package odoo.miem.android.feature.employees.impl.employeesScreen.employeeDetails.helpers

import odoo.miem.android.common.network.employees.api.entities.EmployeeDetails
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screen.employeeDetailsLike.models.EmployeeDetailsHeader
import odoo.miem.android.feature.employees.impl.R

internal fun getDetailsHeader(employeeDetails: EmployeeDetails) = EmployeeDetailsHeader(
    id = employeeDetails.id,
    name = employeeDetails.name,
    email = employeeDetails.email,
    mobilePhone = employeeDetails.mobilePhone,
    workPhone = employeeDetails.workPhone,
    company = employeeDetails.company,
    avatarLink = employeeDetails.avatarLink
)

internal fun getDetailsPages(
    stringResolver: (stringRes: Int) -> String,
    employeeDetails: EmployeeDetails
) = listOf(
    getDetailedInfoPage(
        stringResolver = stringResolver,
        employeeDetails = employeeDetails
    ),
    getAboutMePage(
        topic = stringResolver(R.string.employees_details_title_about_me),
        aboutMe = employeeDetails.aboutMe
    )
)

private fun getDetailedInfoPage(
    stringResolver: (stringRes: Int) -> String,
    employeeDetails: EmployeeDetails
) = DetailedInfoType(
    blocks = mapOf(
        stringResolver(R.string.employee_details_title_main_info) to listOf(
            DetailedInfoType.TextType(
                key = stringResolver(R.string.employee_details_title_department),
                text = employeeDetails.department
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.employee_details_title_study_group),
                text = employeeDetails.studyGroup
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.employee_details_title_manager),
                text = employeeDetails.manager
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.employee_details_title_coach),
                text = employeeDetails.coach
            ),
        ),
        stringResolver(R.string.employees_details_title_work_info) to listOf(
            DetailedInfoType.TextType(
                key = stringResolver(R.string.employees_details_title_work_address),
                text = employeeDetails.address
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.employees_details_title_work_location),
                text = employeeDetails.workLocation
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.employee_details_title_working_hours),
                text = employeeDetails.resourceCalendar
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.employee_details_title_timezone),
                text = employeeDetails.timeZone
            ),
        )
    )
)

private fun getAboutMePage(topic: String, aboutMe: String?) = TextType(
    topic = topic,
    text = aboutMe
)
