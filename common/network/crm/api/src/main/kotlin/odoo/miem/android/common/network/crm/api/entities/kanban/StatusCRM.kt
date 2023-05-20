package odoo.miem.android.common.network.crm.api.entities.kanban

import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel

data class StatusCRM(
    override val statusName: String,
    override val employees: List<OpportunityCRM>,
    override val iconId: Int = 0,
    override val id: Long = 0,
    val totalPayment: Double = 0.0
) : RecruitmentLikeStatusModel<OpportunityCRM>
