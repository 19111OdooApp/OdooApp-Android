package odoo.miem.android.core.networkApi.recruitment.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class RecruitmentKanbanStagesResponse(

    @Json(name = "groups")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val stages: List<Stage>?
) {

    @JsonClass(generateAdapter = true)
    data class Stage(

        // [id, name]
        @Json(name = "stage_id")
        @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
        val stageInfo: List<Any>,
    )
}
