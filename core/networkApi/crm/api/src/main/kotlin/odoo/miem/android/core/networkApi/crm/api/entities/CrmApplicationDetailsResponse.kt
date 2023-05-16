package odoo.miem.android.core.networkApi.crm.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

// TODO Remake
@JsonClass(generateAdapter = true)
data class CrmApplicationDetailsResponse(

    // Employee Info
    @Json(name = "id")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val id: Long?,

    @Json(name = "stage_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val stageInfo: List<Any>?, // [id, name]

    @Json(name = "partner_name")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val partnerName: String?,

    // Take this, if partnerName is empty
    @Json(name = "name")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val opportunityName: String?,

    @Json(name = "contact_name")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val fullName: String?,


    @Json(name = "phone")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val sellerPhone: String?,

    @Json(name = "mobile")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val sellerMobile: String?,

    @Json(name = "email_from")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val sellerEmail: String?,


    @Json(name = "company_currency")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val companyCurrency: List<Any>?,

    @Json(name = "expected_revenue")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val expectedRevenue: Double?,


    @Json(name = "street")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val opportunityStreet: String?,

    @Json(name = "city")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val opportunityCity: String?,

    @Json(name = "city")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val opportunityStateInfo: List<Any>?,

    @Json(name = "zip")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val opportunityZip: String?,

    @Json(name = "country_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val opportunityCountryInfo: List<Any>?,


    @Json(name = "website")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val opportunityWebsite: String?,

    @Json(name = "date_deadline")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val opportunityDeadline: String?,

    @Json(name = "priority")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val opportunityRating: String?,


    @Json(name = "user_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val sellerInfo: List<Any>?,

    @Json(name = "team_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val sellerTeamUserInfo: List<Any>?,

    @Json(name = "company_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val sellerCompanyUserInfo: List<Any>?,

    @Json(name = "description")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val opportunitySummary: String?,


    @Json(name = "campaign_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val campaignInfo: List<Any>?,

    @Json(name = "medium_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val mediumInfo: List<Any>?,

    @Json(name = "source_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val sourceInfo: List<Any>?,


    @Json(name = "day_open")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val dayToAssign: Long?,

    @Json(name = "day_close")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val dayToClose: Long?,


    @Json(name = "activity_ids")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val activityIds: List<Long>?,

    @Json(name = "message_ids")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val messageIds: List<Long>?,
)
