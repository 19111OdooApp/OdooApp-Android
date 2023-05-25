package odoo.miem.android.common.network.recruitment.api.entities.kanban

import odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.model.RecruitmentLikeEmployeeModel

data class Employee(
    override val name: String,
    override val rating: Double,
    override val imageUrl: String?,
    override val id: Long,
    override val deadlineStatus: DeadlineStatus,
    override val iconId: Int = 0,
) : RecruitmentLikeEmployeeModel
