package odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.model

interface RecruitmentLikeStatusModel<E : RecruitmentLikeEmployeeModel> {
    val statusName: String
    val employees: List<E>
    val iconId: Int
    val id: Long
}
