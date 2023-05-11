package odoo.miem.android.common.network.recruitment.impl.helper

import odoo.miem.android.common.network.recruitment.api.entities.kanban.Employee
import odoo.miem.android.common.network.recruitment.api.entities.kanban.Status
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel

data class MutableStatus(
    override val statusName: String,
    override val employees: MutableList<Employee> = mutableListOf(),
    override val iconId: Int,
    override val id: Long
) : RecruitmentLikeStatusModel<Employee>

fun MutableStatus.toStatus() = Status(
    statusName = statusName,
    employees = employees,
    iconId = iconId,
    id = id
)
