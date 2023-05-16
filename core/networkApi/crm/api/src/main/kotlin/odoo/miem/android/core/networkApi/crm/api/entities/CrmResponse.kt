package odoo.miem.android.core.networkApi.crm.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class CrmResponse(

    @Json(name = "length")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val length: Int? = null,

    @Json(name = "records")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val records: List<Record>? = null
) {

    @JsonClass(generateAdapter = true)
    data class Record(

        @Json(name = "id")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val id: Long?,

        @Json(name = "priority")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val rating: String?,

        // [id, stage name]
        @Json(name = "stage_id")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val stageInfo: List<Any>?,

        // [id, stage name]
        @Json(name = "user_id")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val creatorInfo: List<Any>?,

        @Json(name = "partner_id")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val partnerNameInfo: List<Any>?,

        // If empty partnerName, use this
        @Json(name = "name")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val name: String?,

        @Json(name = "user_email")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val email: String?,

        @Json(name = "activity_summary")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val summary: String?,

        @Json(name = "expected_revenue")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val expectedRevenue: Double?,

        @Json(name = "company_currency")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val companyCurrencyInfo: List<Any>?,

        @Json(name = "activity_state")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val activityState: String?,
    )
}
