package odoo.miem.android.feature.recruitment.impl.screen.jobs.model

import odoo.miem.android.common.uiKitComponents.screen.searchLike.model.SearchLikeModel

// TODO Move to domain
data class RecruitmentJob(
    override val name: String
) : SearchLikeModel
