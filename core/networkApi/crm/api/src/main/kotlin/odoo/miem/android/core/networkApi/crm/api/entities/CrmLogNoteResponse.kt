package odoo.miem.android.core.networkApi.crm.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class CrmLogNoteResponse(

    @Json(name = "id")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val id: Long?,

    @Json(name = "date")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val date: String?,

    @Json(name = "body")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val message: String?,

    @Json(name = "author_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val authorInfo: List<Any>?, // [id, name]

    @Json(name = "subtype_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val postInfo: List<Any>?, // [id, post type]

    @Json(name = "tracking_value_ids")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val trackingInfoList: List<TrackingInfo>?,

    @Json(name = "subtype_description")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val subtypeDescription: String?,
) {
    @JsonClass(generateAdapter = true)
    data class TrackingInfo(

        @Json(name = "id")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val id: Long?,

        @Json(name = "changed_field")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val changedField: String?,

        @Json(name = "old_value")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val oldValue: String?,

        @Json(name = "new_value")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val newValue: String?,
    )
}
