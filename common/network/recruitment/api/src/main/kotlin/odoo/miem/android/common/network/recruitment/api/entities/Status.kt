package odoo.miem.android.common.network.recruitment.api.entities

import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel

data class Status(
    override val statusName: String,
    override val employees: List<Employee>,
    override val imageUrl: String?
) : RecruitmentLikeStatusModel<Employee>
