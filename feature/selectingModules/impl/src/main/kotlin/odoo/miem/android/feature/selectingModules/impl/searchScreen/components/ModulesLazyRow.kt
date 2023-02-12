package odoo.miem.android.feature.selectingModules.impl.searchScreen.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.uiKitComponents.cards.SmallModuleCard
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding

/**
 * [ModulesLazyRow] - row with modules for [SearchRecommendationsContent]
 * Looks like header with horizontally spaced module cards
 *
 * @see [SearchRecommendationsContent]
 *
 * @author Egor Danilov
 */
@Suppress("MagicNumber")
@Composable
fun ModulesLazyRow(
    @StringRes headerRes: Int,
    modules: List<OdooModule>,
    onModuleCardClick: (OdooModule) -> Unit = {},
    onLikeModuleClick: (OdooModule) -> Unit = {},
) {
    val itemSpacing = mainHorizontalPadding / 2
    val startRowPadding = mainHorizontalPadding / 2
    val endRowPadding = mainHorizontalPadding / 4

    LabelText(
        textRes = headerRes,
        modifier = Modifier.padding(start = 34.dp)
    )

    Spacer(modifier = Modifier.height(mainVerticalPadding))

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Spacer(modifier = Modifier.width(startRowPadding))
        }

        items(modules) { module ->
            with(module) {
                SmallModuleCard(
                    moduleName = this.name,
                    isLiked = this.isFavourite,
                    onClick = { onModuleCardClick(this) },
                    onLikeClick = {
                        onLikeModuleClick(this)
                    },
                    modifier = Modifier.width(170.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.width(endRowPadding))
        }
    }
}
