package odoo.miem.android.common.uiKitComponents.screen.employeesLike.employeeDetailsLike.models

import odoo.miem.android.common.utils.avatar.AvatarRequestHeader

data class EmployeeDetailsHeader(
    val id: Long?,
    val name: String?,
    val email: String?,
    val mobilePhone: String?,
    val workPhone: String?,
    val company: String?,
    val avatarLink: String?,
    val avatarRequestHeaders: List<AvatarRequestHeader>
)
