package odoo.miem.android.feature.selectingModules.impl.searchScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.feature.selectingModules.impl.R
import odoo.miem.android.feature.selectingModules.impl.searchScreen.SearchModulesScreen

/**
 * [SearchRecommendationsContent] is located in [SearchModulesScreen]
 * Looks like two lazy rows with favourite and all modules
 * Disappears when the search began
 *
 * @author Egor Danilov
 */
@Composable
fun SearchRecommendationsContent(
    allModules: List<OdooModule>,
    favouriteModules: List<OdooModule>,
    onModuleCardClick: () -> Unit,
    onLikeModuleClick: (OdooModule) -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (favouriteModules.isNotEmpty()) {
            ModulesLazyRow(
                headerRes = R.string.favourite_modules_header,
                modules = favouriteModules,
                onModuleCardClick = onModuleCardClick,
                onLikeModuleClick = onLikeModuleClick
            )
        }

        if (allModules.isNotEmpty()) {
            Spacer(modifier = Modifier.height(mainVerticalPadding))

            ModulesLazyRow(
                headerRes = R.string.all_modules_header,
                modules = allModules,
                onModuleCardClick = onModuleCardClick,
                onLikeModuleClick = onLikeModuleClick
            )
        }
    }
}
