package odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.model

sealed class RecruitmentBottomSheetState {

    data class ChangeStatus<E : RecruitmentLikeEmployeeModel>(val employee: E) :
        RecruitmentBottomSheetState()

    object CreateStatus : RecruitmentBottomSheetState()

    object Empty : RecruitmentBottomSheetState()
}
