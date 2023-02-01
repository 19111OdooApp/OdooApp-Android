package odoo.miem.android.core.networkApi.authorization.api.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoResponse(
    @Json(name = "jsonrpc") val jsonrpc: String,
    @Json(name = "id") val id: Int,
    @Json(name = "result") val result: ResponseResult
)

@JsonClass(generateAdapter = true)
data class ResponseResult(
    @Json(name = "length") val length: Int,
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