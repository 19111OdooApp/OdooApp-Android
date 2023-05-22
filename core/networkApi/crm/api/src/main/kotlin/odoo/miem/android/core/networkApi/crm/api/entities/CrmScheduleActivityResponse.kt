package odoo.miem.android.core.networkApi.crm.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class CrmScheduleActivityResponse(

    @Json(name = "id")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val id: Long?,

    @Json(name = "activity_type_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val activityInfo: List<Any>?,

    @Json(name = "note")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val note: String?,

    @Json(name = "create_uid")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val createUserInfo: List<Any>?,

    @Json(name = "user_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val assignUserInfo: List<Any>?,

    @Json(name = "write_date")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val createDate: String?,

    @Json(name = "date_deadline")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val deadline: String?,

    @Json(name = "state")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val state: String?,
)
