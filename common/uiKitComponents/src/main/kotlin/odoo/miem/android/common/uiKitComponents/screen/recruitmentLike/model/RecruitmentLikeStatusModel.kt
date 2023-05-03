package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model

interface RecruitmentLikeStatusModel<E : RecruitmentLikeEmployeeModel> {
    val statusName: String
    val employees: List<E>
    val id: Int
}
