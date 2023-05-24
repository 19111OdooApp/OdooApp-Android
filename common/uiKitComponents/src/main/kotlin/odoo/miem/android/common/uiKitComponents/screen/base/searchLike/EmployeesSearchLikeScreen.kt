package odoo.miem.android.common.uiKitComponents.screen.base.searchLike

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mxalbert.sharedelements.SharedElementsRoot
import odoo.miem.android.common.uiKitComponents.buttons.SelectModulesFloatingActionButton
import odoo.miem.android.common.uiKitComponents.screen.base.searchLike.components.BaseMainContent
import odoo.miem.android.common.uiKitComponents.screen.base.searchLike.components.BaseSearchingContent
import odoo.miem.android.common.uiKitComponents.screen.base.searchLike.model.ScreenPage
import odoo.miem.android.common.uiKitComponents.screen.base.searchLike.model.SearchLikeModel
import odoo.miem.android.common.uiKitComponents.utils.SharedElementConstants

@Composable
fun <T : SearchLikeModel> SearchLikeScreen(
    screenPage: ScreenPage<T>,
    filteredItems: List<T>,
    userName: String,
    mainTitle: String,
    @StringRes searchBarPlaceholder: Int,
    sharedKey: String = "sharedKey",
    sharedScreenKey: String = "sharedScreenKey",
    onUserIconClick: () -> Unit = {},
    performSearch: (String) -> Unit = {},
    onSearchBarValueChange: () -> Unit = {},
    isSearchLoading: Boolean = false,
    onNavigateToModulesPressed: () -> Unit = {},
    mainListContent: @Composable (ColumnScope.(items: ScreenPage<T>) -> Unit) = {},
    searchResultListContent: @Composable (ColumnScope.(items: ScreenPage<T>) -> Unit) = {},
    searchStartListContent: @Composable (ColumnScope.(items: List<T>) -> Unit)? = null,
) = Scaffold(
    floatingActionButton = {
        SelectModulesFloatingActionButton(
            onClick = onNavigateToModulesPressed
        )
    },
    backgroundColor = MaterialTheme.colorScheme.background
) { paddingValues ->
    SharedElementsRoot {
        var isSearchScreenContentVisible by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
        ) {
            Crossfade(
                targetState = isSearchScreenContentVisible,
                animationSpec = tween(durationMillis = SharedElementConstants.transitionDurationMills),
                modifier = Modifier.padding(paddingValues)
            ) { visible ->
                if (visible) {
                    BaseSearchingContent(
                        sharedKey = sharedKey,
                        sharedScreenKey = sharedScreenKey,
                        screenPage = screenPage,
                        filteredItems = filteredItems,
                        searchBarPlaceholder = searchBarPlaceholder,
                        onBackPressed = { isSearchScreenContentVisible = false },
                        performSearch = performSearch,
                        onSearchBarValueChange = onSearchBarValueChange,
                        isSearchLoading = isSearchLoading,
                        searchResultListContent = searchResultListContent,
                        searchStartListContent = searchStartListContent
                    )
                } else {
                    BaseMainContent(
                        sharedKey = sharedKey,
                        sharedScreenKey = sharedScreenKey,
                        userName = userName,
                        title = mainTitle,
                        searchBarPlaceholder = searchBarPlaceholder,
                        onUserIconClick = onUserIconClick,
                        onSearchBarClick = { isSearchScreenContentVisible = true },
                        mainListContent = { mainListContent(screenPage) }
                    )
                }
            }
        }
    }
}
