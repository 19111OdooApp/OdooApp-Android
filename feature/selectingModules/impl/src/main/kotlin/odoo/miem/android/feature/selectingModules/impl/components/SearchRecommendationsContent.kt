package odoo.miem.android.feature.selectingModules.impl.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import odoo.miem.android.feature.selectingModules.impl.data.OdooModule
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
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (favouriteModules.isNotEmpty()) {
            ModulesLazyRow(modules = favouriteModules)
        }

        if (allModules.isNotEmpty()) {
            ModulesLazyRow(modules = allModules)
        }
    }
}
