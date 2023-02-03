package odoo.miem.android.core.networkApi.selectingModules.api.source

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

@JsonClass(generateAdapter = true)
data class OdooGroupsResponse(
    @Json(name = "records") val records: List<ResponseRecord>
) {
    @JsonClass(generateAdapter = true)
    data class ResponseRecord(
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String,
        @Json(name = "users") val users: List<Int>
    )
}

@JsonClass(generateAdapter = true)
data class OdooModulesResponse(
    @Json(name = "records") val records: List<ResponseRecord>
) {
    @JsonClass(generateAdapter = true)
    data class ResponseRecord(
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String,
        @Json(name = "complete_name") val fullName: String,
        @Json(name = "parent_id") val parentId: Any,
        @Json(name = "child_id") val childIds: List<Int>,
        @Json(name = "groups_id") val groupIds: List<Int>
    )
}