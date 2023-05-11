package odoo.miem.android.core.networkApi.recruitment.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class RecruitmentJobsResponse(

    @Json(name = "length")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val length: Int?,

    @Json(name = "records")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val jobs: List<Job>?
) {

    @JsonClass(generateAdapter = true)
    data class Job(

        @Json(name = "id")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val id: Long?,

        @Json(name = "name")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val name: String?,

        @Json(name = "state")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val state: String?,

        @Json(name = "is_favorite")
        @SpecifiedTypeOrNull(JsonReader.Token.BOOLEAN)
        val isFavorite: Boolean?,

        // [id, department name]
        @Json(name = "department_id")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val departmentId: List<Any>?,

        @Json(name = "no_of_recruitment")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val numberToRecruit: Int?,

        @Json(name = "new_application_count")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val numberOfNewApplication: Int?,

        @Json(name = "application_count")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val numberOfApplication: Int?,

        @Json(name = "website_url")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val websiteUrl: String?,

        @Json(name = "is_published")
        @SpecifiedTypeOrNull(JsonReader.Token.BOOLEAN)
        val isPublished: Boolean?,
    )
}
