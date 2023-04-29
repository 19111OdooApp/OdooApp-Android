package odoo.miem.android.core.networkApi.recruitment.api.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecruitmentResponse(

    @Json(name = "length")
    val length: Int,

    @Json(name = "records")
    val records: List<Record>
) {

    @JsonClass(generateAdapter = true)
    data class Record(

        @Json(name = "id")
        val id: Long,

        @Json(name = "priority")
        val rating: Double,

        // [id, job name]
        @Json(name = "job_id")
        val jobInfo: List<Any>,

        // [id, stage name]
        @Json(name = "stage_id")
        val stageInfo: List<Any>,

        // [id, stage name]
        @Json(name = "department_id")
        val departmentInfo: List<Any>,

        // [id, stage name]
        @Json(name = "user_id")
        val creatorInfo: List<Any>,

        @Json(name = "partner_name")
        val name: String,

        @Json(name = "user_email")
        val email: String,

        @Json(name = "activity_summary")
        val summary: Any,

        @Json(name = "salary_proposed")
        val salaryProposed: Float,

        @Json(name = "salary_expected")
        val salaryExpected: Float,

        @Json(name = "create_date")
        val createdDate: String,
    )
}
