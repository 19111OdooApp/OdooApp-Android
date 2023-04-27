package odoo.miem.android.core.networkApi.userInfo.api.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoResponse(
    @Json(name = "records") val records: List<ResponseRecord>
) {
    @JsonClass(generateAdapter = true)
    data class ResponseRecord(
        @Json(name = "id") val modelId: Int,
        @Json(name = "user_id") val userInfo: List<Any>,
        @Json(name = "x_favourite_modules") val favouriteModules: Any
    )
}

data class ImplementedModules(
    @Json(name = "modules") val modules: List<String>
)
