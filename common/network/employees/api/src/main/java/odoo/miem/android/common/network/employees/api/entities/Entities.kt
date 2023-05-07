package odoo.miem.android.common.network.employees.api.entities

data class EmployeeBasicInfo(
    val id: Int,
    val name: String,
    val job: String?,
    val email: String?,
    val phone: String?,
    val avatar: String
)

data class EmployeeDetails(
    val id: Int,
    val name: String,
    val job: String?,
    val mobilePhone: String?,
    val workPhone: String?,
    val email: String?,
    val department: String?,
    val studyGroup: String?,
    val company: String?,
    val address: String?,
    val workLocation: String?,
    val resourceCalendar: String?,
    val aboutMe: String?
)