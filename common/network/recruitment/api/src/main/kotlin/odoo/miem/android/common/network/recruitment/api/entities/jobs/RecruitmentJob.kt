package odoo.miem.android.common.network.recruitment.api.entities.jobs

import odoo.miem.android.common.uiKitComponents.screen.base.searchLike.model.SearchLikeModel

data class RecruitmentJob(
    val id: Long,
    override val name: String,
    val isFavorite: Boolean,
    val state: RecruitmentJobState,
    val numberToRecruit: Int,
    val numberOfNewApplication: Int,
    val numberOfApplication: Int,
    val url: String,
    val isPublished: Boolean,
) : SearchLikeModel

enum class RecruitmentJobState {

    RECRUIT_START,

    RECRUIT_DONE,
}
