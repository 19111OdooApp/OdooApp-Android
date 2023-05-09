package odoo.miem.android.core.networkApi.recruitment.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class RecruitmentKanbanStagesResponse(

    @Json(name = "length")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val length: Int?,

    @Json(name = "records")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val stages: List<Stage>?
) {

    @JsonClass(generateAdapter = true)
    data class Stage(

        @Json(name = "id")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val id: Long?,

        @Json(name = "display_name")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val name: String?,

        @Json(name = "job_ids")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val jobIds: List<Long>?,
    )
}
