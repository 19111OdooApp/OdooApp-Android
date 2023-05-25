package odoo.miem.android.core.networkApi.userInfo.api.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class UserInfoResponse(

    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    @Json(name = "records")
    val records: List<ResponseRecord>?
) {
    @JsonClass(generateAdapter = true)
    data class ResponseRecord(

        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        @Json(name = "id")
        val modelId: Int?,

        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        @Json(name = "user_id")
        val userInfo: List<Any>?
    )
}
