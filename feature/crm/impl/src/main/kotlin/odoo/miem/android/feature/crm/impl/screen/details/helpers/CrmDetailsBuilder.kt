package odoo.miem.android.feature.crm.impl.screen.details.helpers

import android.content.Context
import odoo.miem.android.common.network.crm.api.entities.details.LogNoteInfo
import odoo.miem.android.common.network.crm.api.entities.details.OpportunityInfo
import odoo.miem.android.common.network.crm.api.entities.details.ScheduleActivityInfo
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.DividedListType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.bottomSheet.types.DetailedBottomSheetComponentType
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeDividedListItem
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DetailsLikeHeader
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.models.DividedListItemAction
import odoo.miem.android.feature.crm.impl.R

internal fun getDetailsHeader(
    opportunityInfo: OpportunityInfo,
    majorTitle: String
) = DetailsLikeHeader(
    title = opportunityInfo.opportunityName,
    majorSubtitle = majorTitle,
    minorSubtitle = null
)

internal fun getDetailsPages(
    stringResolver: (stringRes: Int) -> String,
    opportunityInfo: OpportunityInfo,
    onCreateLogNote: (text: String) -> Unit,
    context: Context
) = listOf(
    getDetailedInfoPage(
        stringResolver = stringResolver,
        opportunityInfo = opportunityInfo
    ),
    getSummaryPage(
        topic = stringResolver(R.string.crm_details_title_internal_notes),
        summary = opportunityInfo.opportunitySummary
    ),
    getLogNotePage(
        logNotes = opportunityInfo.logNotes,
        stringResolver = stringResolver,
        onCreateLogNote = onCreateLogNote
    ),
    getScheduleActivitiesPage(
        activities = opportunityInfo.scheduleActivities,
        stringResolver = stringResolver,
        context = context
    )
)

private fun getDetailedInfoPage(
    stringResolver: (stringRes: Int) -> String,
    opportunityInfo: OpportunityInfo,
) = DetailedInfoType(
    blocks = mapOf(
        stringResolver(R.string.crm_details_title_general_information) to listOf(
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_seller_name),
                text = opportunityInfo.sellerName,
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_seller_phone),
                text = opportunityInfo.sellerPhone,
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_seller_mobile),
                text = opportunityInfo.sellerMobile,
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_seller_email),
                text = opportunityInfo.sellerEmail,
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_team),
                text = opportunityInfo.sellerTeamUserName
            ),
            DetailedInfoType.RatingType(
                key = stringResolver(R.string.crm_details_title_priority),
                rating = opportunityInfo.opportunityRating
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_company_name),
                text = opportunityInfo.sellerCompanyUserName
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_deadline),
                text = opportunityInfo.opportunityDeadline
            ),
        ),
        stringResolver(R.string.crm_details_title_contacts) to listOf(
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_full_name),
                text = opportunityInfo.fullName
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_zip),
                text = opportunityInfo.opportunityZip
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_country),
                text = opportunityInfo.opportunityCountryName
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_state),
                text = opportunityInfo.opportunityStateName
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_city),
                text = opportunityInfo.opportunityCity
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_street),
                text = opportunityInfo.opportunityStreet
            ),
        ),
        stringResolver(R.string.crm_details_title_media) to listOf(
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_website),
                text = opportunityInfo.opportunityWebsite
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_medium),
                text = opportunityInfo.mediumName
            ),
            DetailedInfoType.TextType(
                key = stringResolver(R.string.crm_details_title_source),
                text = opportunityInfo.sourceName
            ),
        ),
        stringResolver(R.string.crm_details_title_dates_information) to listOf(
            DetailedInfoType.NumberType(
                key = stringResolver(R.string.crm_details_title_date_assign),
                number = opportunityInfo.dayToAssign?.toDouble() ?: 0.0
            ),
            DetailedInfoType.NumberType(
                key = stringResolver(R.string.crm_details_title_date_close),
                number = opportunityInfo.dayToClose?.toDouble() ?: 0.0
            ),
        ),

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
    override val topic: String = stringResolver(R.string.crm_details_title_log_note)

    override val items: List<DetailsLikeDividedListItem> = logNotes.map {
        object : DetailsLikeDividedListItem {
            override val topic: String =
                it.authorName ?: stringResolver(odoo.miem.android.common.uiKitComponents.R.string.default_user_name)
            override val userName: String =
                it.authorName ?: stringResolver(odoo.miem.android.common.uiKitComponents.R.string.default_user_name)
            override val avatarUrl: String? = null
            override val description: String = it.resolveDescription()
            override val date: String = it.date ?: ""
            override val actions: List<DividedListItemAction> = emptyList()
        }
    }

    override val sheetElements: List<DetailedBottomSheetComponentType> = listOf(
        DetailedBottomSheetComponentType.BigTextComponentType(
            placeholderText = stringResolver(R.string.crm_details_title_enter_log_note)
        ),
    )
    override val onDone: (results: List<DetailedBottomSheetComponentType>) -> Unit = { list ->
        list.firstOrNull()?.result?.let {
            onCreateLogNote(it)
        }
    }

    override val bottomSheetButtonText: String =
        stringResolver(R.string.crm_details_title_new_log_note)
}

private fun getScheduleActivitiesPage(
    activities: List<ScheduleActivityInfo>,
    stringResolver: (stringRes: Int) -> String,
    context: Context
) = object : DividedListType {
    override val topic: String =
        stringResolver(R.string.crm_details_title_schedule_activity)

    override val items: List<DetailsLikeDividedListItem> = activities.map {
        object : DetailsLikeDividedListItem {
            override val topic: String =
                context.resources.getString(R.string.crm_details_title_due_date).format(
                    it.deadline,
                    it.activityName
                )
            override val userName: String =
                it.assignUserName ?: stringResolver(odoo.miem.android.common.uiKitComponents.R.string.default_user_name)
            override val avatarUrl: String? = null
            override val description: String = it.resolveDescription()
            override val date: String =
                context.resources.getString(R.string.crm_details_title_assign_for).format(
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
