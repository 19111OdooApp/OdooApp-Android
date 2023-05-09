package odoo.miem.android.common.network.recruitment.api.entities.kanban

import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel

data class Status(
    override val statusName: String,
    override val employees: List<Employee>,
    override val iconId: Int = 0,
    override val id: Long = 0
) : RecruitmentLikeStatusModel<Employee>
