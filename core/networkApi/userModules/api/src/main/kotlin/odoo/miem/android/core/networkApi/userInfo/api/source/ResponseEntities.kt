package odoo.miem.android.core.networkApi.userInfo.api.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class OdooGroupsResponse(

    @Json(name = "records")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val records: List<ResponseRecord>?
) {
    @JsonClass(generateAdapter = true)
    data class ResponseRecord(

        @Json(name = "id")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val id: Int?,

        @Json(name = "name")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val name: String?,

        @Json(name = "users")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val users: List<Int>?
    )
}

@JsonClass(generateAdapter = true)
data class OdooModulesResponse(

    @Json(name = "records")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val records: List<ResponseRecord>?
) {
    @JsonClass(generateAdapter = true)
    data class ResponseRecord(

        @Json(name = "id")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val id: Int?,

        @Json(name = "name")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val name: String?,

        @Json(name = "parent_id")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val parentId: List<Any>?,

        @Json(name = "child_id")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val childIds: List<Int>?,

        @Json(name = "groups_id")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val groupIds: List<Int>?
    )
}
