package odoo.miem.android.core.networkApi.employees.api.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonReader
import odoo.miem.android.core.jsonrpc.converter.api.annotation.SpecifiedTypeOrNull

@JsonClass(generateAdapter = true)
data class AllEmployeesResponse(

    @Json(name = "records")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val records: List<EmployeeBasicInfoResponse>?
) {
    @JsonClass(generateAdapter = true)
    data class EmployeeBasicInfoResponse(

        @Json(name = "id")
        @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
        val id: Long?,

        @Json(name = "name")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val employeeName: String?,

        @Json(name = "job_title")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val job: String?,

        @Json(name = "work_email")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val email: String?,

        @Json(name = "work_phone")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val phone: String?,

        @Json(name = "avatar_1920")
        @SpecifiedTypeOrNull(JsonReader.Token.STRING)
        val avatar: String?,
    )
}

@JsonClass(generateAdapter = true)
data class EmployeeDetailsResponse(

    @Json(name = "id")
    @SpecifiedTypeOrNull(JsonReader.Token.NUMBER)
    val id: Long?,

    @Json(name = "name")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val employeeName: String?,

    @Json(name = "job_title")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val job: String?,

    @Json(name = "mobile_phone")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val mobilePhone: String?,

    @Json(name = "work_phone")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val workPhone: String?,

    @Json(name = "work_email")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val email: String?,

    // [id, department_name]
    @Json(name = "department_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val departmentId: List<Any>?,

    // [id, study_group_name]
    @Json(name = "studygroup_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val studyGroup: List<Any>?,

    // [id, company_name]
    @Json(name = "company_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val company: List<Any>?,

    // [id, address]
    @Json(name = "address_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val address: List<Any>?,

    // [id, work_location]
    @Json(name = "work_location_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val workLocation: List<Any>?,

    @Json(name = "resource_calendar_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val resourceCalendar: List<Any>?,

    @Json(name = "cv")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val aboutMe: String?,

    // [id, coach_name]
    @Json(name = "coach_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val coach: List<Any>?,

    // [id, manager_name]
    @Json(name = "parent_id")
    @SpecifiedTypeOrNull(JsonReader.Token.BEGIN_ARRAY)
    val manager: List<Any>?,

    @Json(name = "employee_type")
    @SpecifiedTypeOrNull(JsonReader.Token.STRING)
    val employeeType: String?
)
