package odoo.miem.android.common.network.crm.impl.helper

import odoo.miem.android.common.network.crm.api.entities.kanban.EmployeeCRM
import odoo.miem.android.common.network.crm.api.entities.kanban.StatusCRM
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel

data class MutableStatusCRM(
    override val statusName: String,
    override val employees: MutableList<EmployeeCRM> = mutableListOf(),
    override val iconId: Int,
    override val id: Long
) : RecruitmentLikeStatusModel<EmployeeCRM>

fun MutableStatusCRM.toStatusCRM() = StatusCRM(
    statusName = statusName,
    employees = employees,
    iconId = iconId,
    id = id
)
