package odoo.miem.android.common.network.crm.api.entities.details

data class OpportunityInfo(

    // Employee Info
    val id: Long,

    val stageName: String?,

    val partnerName: String?,

    val opportunityName: String?,

    val fullName: String?,

    // Seller info
    val sellerPhone: String?,

    val sellerMobile: String?,

    val sellerEmail: String?,

    val companyCurrencySymbol: String,

    val expectedRevenue: Double?,

    val sellerName: String?,

    val sellerTeamUserName: String?,

    val sellerCompanyUserName: String?,

    // Opportunity info
    val opportunityStreet: String?,

    val opportunityCity: String?,

    val opportunityStateName: String?,

    val opportunityZip: String?,

    val opportunityCountryName: String?,

    val opportunityWebsite: String?,

    val opportunityDeadline: String?,

    val opportunityRating: Double,

    val opportunitySummary: String?,

    val campaignName: String?,

    val mediumName: String?,

    val sourceName: String?,

    val dayToAssign: Long?,

    val dayToClose: Long?,

    val activityIds: List<Long>?,

    val messageIds: List<Long>?,
)
