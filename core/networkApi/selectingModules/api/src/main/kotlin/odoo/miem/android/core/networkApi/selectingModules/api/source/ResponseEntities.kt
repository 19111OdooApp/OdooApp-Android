package odoo.miem.android.core.networkApi.selectingModules.api.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoResponse(
    @Json(name = "result") val result: ResponseResult
)

@JsonClass(generateAdapter = true)
data class ResponseResult(
    @Json(name = "records") val records: List<ResponseRecord>
)

@JsonClass(generateAdapter = true)
data class ResponseRecord(
    @Json(name = "id") val id: Int,
    @Json(name = "user_id") val userInfo: User
)

@JsonClass(generateAdapter = true)
data class User(
    val uid: Int,
    val name: String
)

data class OdooGroup(
    val id: Int,
    val name: String,
    val users: List<Int>
)

data class OdooModule(
    val name: String,
    val numberOfNotifications: Int,
    var isLiked: Boolean = false
)