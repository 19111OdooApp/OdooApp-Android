package odoo.miem.android.feature.recruitment.impl.data

import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel

// TODO: Move models to domain
internal data class Employee(
    override val name: String,
    override val rating: Double,
    override val imageUrl: String?,
    override val id: Long? = 0,
    override val deadlineStatus: DeadlineStatus,
) : RecruitmentLikeEmployeeModel
