package odoo.miem.android.feature.recruitment.impl.screen.details.helpers

import android.content.Context
import odoo.miem.android.common.network.recruitment.api.entities.details.ApplicationInfo
import odoo.miem.android.common.network.recruitment.api.entities.details.LogNoteInfo
import odoo.miem.android.common.network.recruitment.api.entities.details.ScheduleActivityInfo
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DividedListType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.bottomSheet.types.DetailedBottomSheetComponentType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeDividedListItem
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeHeader
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DividedListItemAction
import odoo.miem.android.feature.recruitment.impl.R
import odoo.miem.android.common.uiKitComponents.R as uiKitComponentsR

internal fun getDetailsHeader(applicationInfo: ApplicationInfo) = DetailsLikeHeader(
    title = applicationInfo.employeeName,
    majorSubtitle = applicationInfo.employeeEmail,
    minorSubtitle = applicationInfo.employeePhone ?: applicationInfo.employeeMobile
)

internal fun getDetailsPages(
    stringResolver: (stringRes: Int) -> String,
    onCreateLogNote: (text: String) -> Unit,
    applicationInfo: ApplicationInfo,
    context: Context
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
        logNotes = applicationInfo.logNotes,
        onCreateLogNote = onCreateLogNote,
        stringResolver = stringResolver
    ),
    getScheduleActivitiesPage(
        activities = applicationInfo.scheduleActivities,
        stringResolver = stringResolver,
        context = context
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
    logNotes: List<LogNoteInfo>,
    stringResolver: (stringRes: Int) -> String,
    onCreateLogNote: (text: String) -> Unit
) = object : DividedListType {
    override val topic: String = stringResolver(R.string.recruitment_details_title_log_note)

    override val items: List<DetailsLikeDividedListItem> = logNotes.map {
        object : DetailsLikeDividedListItem {
            override val topic: String =
                it.authorName ?: stringResolver(uiKitComponentsR.string.default_user_name)
            override val userName: String =
                it.authorName ?: stringResolver(uiKitComponentsR.string.default_user_name)
            override val avatarUrl: String? = null
            override val description: String = it.resolveDescription()
            override val date: String = it.date ?: ""
            override val actions: List<DividedListItemAction> = emptyList()
        }
    }

    override val sheetElements: List<DetailedBottomSheetComponentType> = listOf(
        DetailedBottomSheetComponentType.BigTextComponentType(
            placeholderText = stringResolver(R.string.recruitment_details_title_enter_log_note)
        ),
    )
    override val onDone: (results: List<DetailedBottomSheetComponentType>) -> Unit = { list ->
        list.firstOrNull()?.result?.let {
            onCreateLogNote(it)
        }
    }

    override val bottomSheetButtonText: String =
        stringResolver(R.string.recruitment_details_title_new_log_note)
}

private fun getScheduleActivitiesPage(
    activities: List<ScheduleActivityInfo>,
    stringResolver: (stringRes: Int) -> String,
    context: Context
) = object : DividedListType {
    override val topic: String =
        stringResolver(R.string.recruitment_details_title_schedule_activity)

    override val items: List<DetailsLikeDividedListItem> = activities.map {
        object : DetailsLikeDividedListItem {
            override val topic: String =
                context.resources.getString(R.string.recruitment_details_title_due_date).format(
                    it.deadline,
                    it.activityName
                )
            override val userName: String =
                it.assignUserName ?: stringResolver(uiKitComponentsR.string.default_user_name)
            override val avatarUrl: String? = null
            override val description: String = it.resolveDescription()
            override val date: String =
                context.resources.getString(R.string.recruitment_details_title_assign_for).format(
                    it.assignUserName
                )
            override val actions: List<DividedListItemAction> = emptyList()
        }
    }

    override val sheetElements: List<DetailedBottomSheetComponentType> = emptyList()

    override val onDone: (results: List<DetailedBottomSheetComponentType>) -> Unit = {}

    override val bottomSheetButtonText: String = ""
}

private fun LogNoteInfo.resolveDescription() = buildString {
    message?.let { append("<p>$it</p>") }
    subtypeDescription?.let { append("<p><b>$it</b></p>") }
    trackingInfoList.forEach {
        append("<p>${it.changedField}: ${it.oldValue} -> ${it.newValue}</p>")
    }
}

private fun ScheduleActivityInfo.resolveDescription() = buildString {
    note?.let { append(it) }
}
