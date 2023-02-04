package odoo.miem.android.feature.selectingModules.impl.searchScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.uiKitComponents.cards.BigModuleCard
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.feature.selectingModules.impl.searchScreen.SearchModulesScreen

/**
 * [SearchResultContent] - search results for [SearchModulesScreen]
 * shows column with [BigModuleCard] items
 *
 * @author Egor Danilov
 */
@Composable
fun SearchResultContent(
    filteredModules: List<OdooModule>,
    onModuleCardClick: () -> Unit = {}
) = LazyColumn(
    verticalArrangement = Arrangement.spacedBy(mainVerticalPadding),
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = mainHorizontalPadding)
        .padding(top = commonPadding)
) {
    items(filteredModules) {
        var isLikedState by remember { mutableStateOf(it.isFavourite) }

        BigModuleCard(
            moduleName = it.name,
            numberOfNotification = it.numberOfNotifications,
            isLiked = isLikedState,
            onClick = onModuleCardClick,
            onLikeClick = { isLikedState = !isLikedState },
        )
    }

    item {
        Spacer(modifier = Modifier.height(mainVerticalPadding / 2))
    }
}
