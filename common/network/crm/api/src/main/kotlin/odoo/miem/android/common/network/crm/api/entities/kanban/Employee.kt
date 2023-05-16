package odoo.miem.android.common.network.crm.api.entities.kanban

import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel

data class EmployeeCRM(
    override val name: String,
    override val rating: Double,
    override val imageUrl: String?,
    override val id: Long,
    override val deadlineStatus: DeadlineStatus,
    override val iconId: Int = 0,
) : RecruitmentLikeEmployeeModel
