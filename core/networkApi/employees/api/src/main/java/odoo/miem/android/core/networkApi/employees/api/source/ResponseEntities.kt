package odoo.miem.android.core.networkApi.employees.api.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllEmployeesResponse(
    @Json(name = "records") val records: List<EmployeeBasicInfoResponse>
) {
    @JsonClass(generateAdapter = true)
    data class EmployeeBasicInfoResponse(
        @Json(name = "id") val id: Int,
        @Json(name = "name") val employeeName: String,
        @Json(name = "job_title") val job: Any,
        @Json(name = "work_email") val email: Any,
        @Json(name = "work_phone") val phone: Any,
        @Json(name = "avatar_1920") val avatar: String,
    )
}

@JsonClass(generateAdapter = true)
data class EmployeeInfoResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val employeeName: String,
    @Json(name = "job_title") val job: Any,
    @Json(name = "mobile_phone") val mobilePhone: Any,
    @Json(name = "work_phone") val workPhone: Any,
    @Json(name = "work_email") val email: Any,
    @Json(name = "department_id") val departmentId: Any,
    @Json(name = "studygroup_id") val studyGroup: Any,
    @Json(name = "company_id") val company: Any,
    @Json(name = "address_id") val address: Any,
    @Json(name = "work_location_id") val workLocation: Any,
    @Json(name = "resource_calendar_id") val resourceCalendar: Any,
    @Json(name = "cv") val aboutMe: Any,
    @Json(name = "coach_id") val coach: Any,
    @Json(name = "parent_id") val manager: Any,
    @Json(name = "employee_type") val employeeType: Any
)
