package odoo.miem.android.feature.recruitment.impl.data

import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel

internal data class Status(
    override val statusName: String,
    override val employees: List<Employee>,
    override val imageUrl: String?
) : RecruitmentLikeStatusModel<Employee>
