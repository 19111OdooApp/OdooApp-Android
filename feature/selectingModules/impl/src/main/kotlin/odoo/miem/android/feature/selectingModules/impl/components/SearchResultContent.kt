package odoo.miem.android.feature.selectingModules.impl.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import odoo.miem.android.common.uiKitComponents.cards.BigModuleCard
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.feature.selectingModules.impl.R
import odoo.miem.android.feature.selectingModules.impl.data.OdooModule
import odoo.miem.android.feature.selectingModules.impl.searchScreen.SearchModulesScreen

/**
 * [SearchResultContent] - search results for [SearchModulesScreen]
 * If result is empty, shows relevant text and sad picture :(
 * Otherwise, shows column with [BigModuleCard] items
 *
 * @author Egor Danilov
 */
@Composable
fun SearchResultContent(
    filteredModules: List<OdooModule>
) {
    val columnModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = mainHorizontalPadding)

    if (filteredModules.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = columnModifier
        ) {
            LabelText(
                textRes = R.string.search_result_empty,
                isLarge = true,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(mainVerticalPadding))

            Image(
                painter = painterResource(R.drawable.ic_sad_smile),
                contentDescription = null,
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(mainVerticalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = columnModifier
        ) {
            items(filteredModules) {
                var isLikedState by remember { mutableStateOf(it.isLiked) }

                BigModuleCard(
                    moduleName = it.name,
                    numberOfNotification = it.numberOfNotifications,
                    isLiked = isLikedState,
                    onLikeClick = { isLikedState = !isLikedState },
                )
            }

            item {
                Spacer(modifier = Modifier.height(mainVerticalPadding / 2))
            }
        }
    }
}
