package odoo.miem.android.common.network.employees.api.entities

import odoo.miem.android.common.uiKitComponents.screen.searchLike.model.SearchLikeModel

data class EmployeeBasicInfo(
    val id: Long,
    override val name: String,
    val job: String?,
    val email: String?,
    val phone: String?,
    val avatarLink: String?,
) : SearchLikeModel

data class EmployeeAvatarRequestHeaders(
    val name: String,
    val value: String
)

data class EmployeeDetails(
    val id: Long?,
    val name: String?,
    val avatarLink: String?,
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
    val aboutMe: String?,
    val coach: String?,
    val manager: String?,
    val employeeType: String?,
    val timeZone: String?
)
