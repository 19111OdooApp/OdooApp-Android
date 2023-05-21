package odoo.miem.android.core.networkApi.userInfo.api.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class UserProfileResponse(

    @Json(name = "id")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val id: Long?,

    @Json(name = "name")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val name: String?,

    @Json(name = "avatar_128")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val avatar128: String?,

    @Json(name = "work_phone")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val workPhone: String?,

    @Json(name = "mobile_phone")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val mobile: String?,

    @Json(name = "email")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val email: String?,

    @Json(name = "tz")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val timezone: String?,

    @Json(name = "company_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val companyInfo: List<Any>?,

    @Json(name = "address_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val addressInfo: List<Any>?,
)
