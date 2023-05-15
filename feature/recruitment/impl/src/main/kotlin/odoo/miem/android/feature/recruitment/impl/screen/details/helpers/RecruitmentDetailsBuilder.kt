package odoo.miem.android.feature.recruitment.impl.screen.details.helpers

import odoo.miem.android.common.network.recruitment.api.entities.details.ApplicationInfo
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeHeader

internal fun getDetailsHeader(applicationInfo: ApplicationInfo) = DetailsLikeHeader(
    title = applicationInfo.employeeName,
    majorSubtitle = applicationInfo.employeeEmail,
    minorSubtitle = applicationInfo.employeePhone ?: applicationInfo.employeeMobile
)

internal fun getDetailsPages(applicationInfo: ApplicationInfo) = listOf(
    getDetailedInfoPage(applicationInfo),
    getSummaryPage(applicationInfo.employeeSummary)
)

private fun getDetailedInfoPage(applicationInfo: ApplicationInfo) = DetailedInfoType(
    blocks = mapOf(
        "Job" to listOf(
            DetailedInfoType.TextType(
                key = "Applied Job",
                text = applicationInfo.jobName
            ),
            DetailedInfoType.TextType(
                key = "Department",
                text = applicationInfo.departmentName
            ),
            DetailedInfoType.TextType(
                key = "Recruiter",
                text = applicationInfo.recruiterName
            ),
            DetailedInfoType.RatingType(
                key = "Appreciation",
                rating = applicationInfo.rating
            ),
            DetailedInfoType.TextType(
                key = "Source",
                text = applicationInfo.recruiterSourceName
            ),
        ),
        "Employee" to listOf(
            DetailedInfoType.TextType(
                key = "Phone",
                text = applicationInfo.employeePhone
            ),
            DetailedInfoType.TextType(
                key = "Mobile",
                text = applicationInfo.employeePhone
            ),
            DetailedInfoType.TextType(
                key = "Email",
                text = applicationInfo.employeeEmail
            ),
        ),
        "Contract" to listOf(
            DetailedInfoType.NumberType(
                key = "Expected Salary",
                number = applicationInfo.salaryExpected ?: 0.0
            ),
            DetailedInfoType.NumberType(
                key = "Proposed Salary",
                number = applicationInfo.salaryProposed ?: 0.0
            )
        )
    )
)

private fun getSummaryPage(summary: String?) = TextType(
    topic = "Application Summary",
    text = summary
)
