package odoo.miem.android.common.uiKitComponents.screen.searchLike

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mxalbert.sharedelements.SharedElementsRoot
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetScaffold
import odoo.miem.android.common.uiKitComponents.bottomsheet.CustomBottomSheetScaffoldState
import odoo.miem.android.common.uiKitComponents.screen.searchLike.components.BaseMainContent
import odoo.miem.android.common.uiKitComponents.screen.searchLike.components.BaseSearchingContent
import odoo.miem.android.common.uiKitComponents.screen.searchLike.model.SearchLikeModel
import odoo.miem.android.common.uiKitComponents.utils.SharedElementConstants

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : SearchLikeModel> SearchLikeScreen(
    items: List<T>,
    scaffoldState: CustomBottomSheetScaffoldState,
    userName: String,
    mainTitle: String,
    sharedKey: String = "sharedKey",
    sharedScreenKey: String = "sharedScreenKey",
    sheetElevation: Dp = 8.dp,
    topRadius: Dp = 35.dp,
    onUserIconClick: () -> Unit = {},
    mainListContent: @Composable (ColumnScope.(items: List<T>) -> Unit) = {},
    searchResultListContent: @Composable (ColumnScope.(items: List<T>) -> Unit) = {},
    searchStartListContent: @Composable (ColumnScope.(items: List<T>) -> Unit)? = null,
    sheetContent: @Composable (ColumnScope.() -> Unit) = {},
) = SharedElementsRoot {
    var isSearchScreenContentVisible by remember { mutableStateOf(false) }

    CustomBottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = sheetContent,
        sheetShape = RoundedCornerShape(
            topStart = topRadius,
            topEnd = topRadius
        ),
        sheetElevation = sheetElevation,
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        possibleValues = scaffoldState.customBottomSheetState.possibleValues,
    ) {
        Crossfade(
            targetState = isSearchScreenContentVisible,
            animationSpec = tween(durationMillis = SharedElementConstants.transitionDurationMills),
        ) { visible ->
            if (visible) {
                BaseSearchingContent(
                    sharedKey = sharedKey,
                    sharedScreenKey = sharedScreenKey,
                    items = items,
                    onBackPressed = { isSearchScreenContentVisible = false },
                    searchResultListContent = searchResultListContent,
                    searchStartListContent = searchStartListContent
                )
            } else {
                BaseMainContent(
                    sharedKey = sharedKey,
                    sharedScreenKey = sharedScreenKey,
                    userName = userName,
                    title = mainTitle,
                    onUserIconClick = onUserIconClick,
                    onSearchBarClick = { isSearchScreenContentVisible = true },
                    mainListContent = { mainListContent(items) }
                )
            }
        }
    }
}
