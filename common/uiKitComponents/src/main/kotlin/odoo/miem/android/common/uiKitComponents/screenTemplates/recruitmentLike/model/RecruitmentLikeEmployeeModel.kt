package odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.model

interface RecruitmentLikeEmployeeModel {
    val name: String
    val imageUrl: String?
    val id: Long
    val rating: Double
    val deadlineStatus: DeadlineStatus
    val iconId: Int
}
