package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model

sealed class RecruitmentBottomSheetState {

    data class ChangeStatus<E : RecruitmentLikeEmployeeModel>(val employee: E) :
        RecruitmentBottomSheetState()

    object CreateStatus : RecruitmentBottomSheetState()

    object Empty : RecruitmentBottomSheetState()
}
