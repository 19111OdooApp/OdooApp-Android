package odoo.miem.android.core.networkApi.userInfo.api.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoResponse(
    @Json(name = "records") val records: List<ResponseRecord>
) {
    @JsonClass(generateAdapter = true)
    data class ResponseRecord(
        @Json(name = "id") val id: Int,
        @Json(name = "user_id") val userInfo: List<Any>
    )
}
