package odoo.miem.android.common.uiKitComponents.bottomsheet

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Stable

/**
 * Custom reference of [BottomSheetScaffoldState]
 *
 * @author Vorozhtsov Mikhail
 */
@ExperimentalMaterialApi
@Stable
class CustomBottomSheetScaffoldState(
    val customBottomSheetState: CustomBottomSheetState
)