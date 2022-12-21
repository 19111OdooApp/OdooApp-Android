package odoo.miem.android.common.uiKitComponents.bottomsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

// TODO Description
@Composable
@ExperimentalMaterialApi
fun rememberCustomBottomSheetState(
    initialValue: CustomBottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (CustomBottomSheetValue) -> Boolean = { true }
): CustomBottomSheetState {
    return rememberSaveable(
        animationSpec,
        saver = CustomBottomSheetState.Saver(
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    ) {
        CustomBottomSheetState(
            initialValue = initialValue,
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    }
}

@Composable
@ExperimentalMaterialApi
fun rememberCustomBottomSheetScaffoldState(
    customBottomSheetState: CustomBottomSheetState = rememberCustomBottomSheetState(CustomBottomSheetValue.Collapsed),
): CustomBottomSheetScaffoldState {
    return remember(customBottomSheetState) {
        CustomBottomSheetScaffoldState(
            customBottomSheetState = customBottomSheetState
        )
    }
}