package odoo.miem.android.feature.recruitment.impl.screen.details.helpers

import odoo.miem.android.common.network.recruitment.api.entities.details.ApplicationInfo
import odoo.miem.android.common.network.recruitment.api.entities.details.LogNoteInfo
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DividedListType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.bottomSheet.types.DetailedBottomSheetComponentType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeDividedListItem
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeHeader
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DividedListItemAction
import odoo.miem.android.feature.recruitment.impl.R

internal fun getDetailsHeader(applicationInfo: ApplicationInfo) = DetailsLikeHeader(
    title = applicationInfo.employeeName,
    majorSubtitle = applicationInfo.employeeEmail,
    minorSubtitle = applicationInfo.employeePhone ?: applicationInfo.employeeMobile
)

internal fun getDetailsPages(
    stringResolver: (stringRes: Int) -> String,
    applicationInfo: ApplicationInfo
) = listOf(
    getDetailedInfoPage(
        stringResolver = stringResolver,
        applicationInfo = applicationInfo
    ),
    getSummaryPage(
        topic = stringResolver(R.string.recruitment_details_title_application_summary),
        summary = applicationInfo.employeeSummary
    ),
    getLogNotePage(
        applicationInfo.logNotes
    )
)

private fun getDetailedInfoPage(
    stringResolver: (stringRes: Int) -> String,
    applicationInfo: ApplicationInfo
) = DetailedInfoType(
    blocks = mapOf(
        stringResolver(R.string.recruitment_details_title_job) to listOf(
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_applied_job),
                text = applicationInfo.jobName
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_department),
                text = applicationInfo.departmentName
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_recruiter),
                text = applicationInfo.recruiterName
            ),
            DetailedInfoType.RatingType(
                key = stringResolver(R.string.recruitment_details_title_appreciation),
                rating = applicationInfo.rating
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_source),
                text = applicationInfo.recruiterSourceName
            ),
        ),
        stringResolver(R.string.recruitment_details_title_employee) to listOf(
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_phone),
                text = applicationInfo.employeePhone
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_mobile),
                text = applicationInfo.employeeMobile
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_email),
                text = applicationInfo.employeeEmail
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_full_name),
                text = applicationInfo.employeeFullName
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_test_task),
                text = applicationInfo.employeeTestTask
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_project),
                text = applicationInfo.employeeProject
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_group),
                text = applicationInfo.employeeGroup
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.recruitment_details_title_specialization),
                text = applicationInfo.employeeSpecialization
            ),
        ),
        stringResolver(R.string.recruitment_details_title_contract) to listOf(
            DetailedInfoType.NumberType(
                key = stringResolver(R.string.recruitment_details_title_expected_salary),
                number = applicationInfo.salaryExpected ?: 0.0
            ),
            DetailedInfoType.NumberType(
                key = stringResolver(R.string.recruitment_details_title_proposed_salary),
                number = applicationInfo.salaryProposed ?: 0.0
            )
        )
    )
)

private fun getSummaryPage(topic: String, summary: String?) = TextType(
    topic = topic,
    text = summary
)

private fun getLogNotePage(
    logNotes: List<LogNoteInfo>
) = object : DividedListType {
    override val topic: String = "Log note"

    override val items: List<DetailsLikeDividedListItem> = logNotes.map {
        object : DetailsLikeDividedListItem {
            override val topic: String = it.authorName ?: "Cool user"
            override val userName: String = it.authorName ?: "Cool user"
            override val avatarUrl: String? = null
            override val description: String = it.resolveDescription()
            override val date: String = it.date ?: ""
            override val actions: List<DividedListItemAction> = emptyList()
        }
    }

    override val sheetElements: List<DetailedBottomSheetComponentType> = listOf(
        DetailedBottomSheetComponentType.BigTextComponentType(
            placeholderText = "Enter log note..."
        ),
        DetailedBottomSheetComponentType.SmallTextComponentType(
            placeholderText = "Summary"
        ),
        DetailedBottomSheetComponentType.ListComponentType(
            placeholderText = "Assigned to",
            values = listOf(
                "Arina Shoshina1",
                "Arina Shoshina2",
                "Arina Shoshina3",
                "Arina Shoshina4",
            )
        ),
        DetailedBottomSheetComponentType.DatePickerComponentType(
            placeholderText = "Due Date"
        ),
    )

    override val bottomSheetButtonText: String = "Add new log note"
}

private fun LogNoteInfo.resolveDescription() = buildString {
    message?.let { append("<p>$it</p>") }
    subtypeDescription?.let { append("<p><b>$it</b></p>") }
    trackingInfoList.forEach {
        append("<p>${it.changedField}: ${it.oldValue} -> ${it.newValue}</p>")
    }
}
