package odoo.miem.android.feature.crm.impl.screen.details.helpers

import odoo.miem.android.common.network.crm.api.entities.details.OpportunityInfo
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.DetailedInfoType
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.screen.base.detailsLike.models.DetailsLikeHeader
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
) = listOf(
    getDetailedInfoPage(
        stringResolver = stringResolver,
        opportunityInfo = opportunityInfo
    ),
    getSummaryPage(
        topic = stringResolver(R.string.crm_details_title_internal_notes),
        summary = opportunityInfo.opportunitySummary
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
