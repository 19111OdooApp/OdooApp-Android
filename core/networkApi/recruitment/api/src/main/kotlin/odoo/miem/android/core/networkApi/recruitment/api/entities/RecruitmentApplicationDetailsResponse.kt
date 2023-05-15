package odoo.miem.android.core.networkApi.recruitment.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class RecruitmentApplicationDetailsResponse(

    // Employee Info
    @Json(name = "id")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val id: Long?,

    @Json(name = "stage_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val stageInfo: List<Any>?, // [id, name]

    @Json(name = "name")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val employeeName: String?,

    @Json(name = "email_from")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val employeeEmail: String?,

    @Json(name = "partner_phone")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val employeePhone: String?,

    @Json(name = "partner_mobile")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val employeeMobile: String?,

    // Recruit Info
    @Json(name = "user_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val recruiterInfo: List<Any>?, // [id, name]

    @Json(name = "priority")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val rating: String?,

    @Json(name = "source_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val recruiterSourceInfo: List<Any>?, // [id, name]

    // Work Info
    @Json(name = "job_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val jobInfo: List<Any>?, // [id, name]

    @Json(name = "department_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val departmentInfo: List<Any>?, // [id, name]

    // Payment Info
    @Json(name = "salary_expected")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val salaryExpected: Double?,

    @Json(name = "salary_proposed")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val salaryProposed: Double?,

    // Employee summary
    @Json(name = "description")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val employeeSummary: String?,

    // Activity & message ids
    @Json(name = "activity_ids")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val activityIds: List<Long>?,

    @Json(name = "message_ids")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val messageIds: List<Long>?,
)
