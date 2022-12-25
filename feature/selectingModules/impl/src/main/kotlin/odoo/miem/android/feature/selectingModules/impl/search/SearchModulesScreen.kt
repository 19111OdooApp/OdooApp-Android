package odoo.miem.android.feature.selectingModules.impl.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.cards.SmallModuleCard
import odoo.miem.android.common.uiKitComponents.text.SubTitleText
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.feature.selectingModules.impl.R
import odoo.miem.android.feature.selectingModules.impl.data.OdooModule

@Composable
private fun SearchModulesScreen(
    searchValue: TextFieldValue = TextFieldValue(),
    onValueChange: (TextFieldValue) -> Unit = {},
    allModules: List<OdooModule>,
    favouriteModules: MutableList<OdooModule>,
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {

    SimpleLogoAppBar()

    Spacer(modifier = Modifier.height(39.dp))

    SearchTextField(
        value = searchValue,
        onValueChange = {
            onValueChange(it)
            // TODO search logic from viewModel
        }
    )
    
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(start = mainHorizontalPadding)
    ) {
        SubTitleText(
            textRes = R.string.favourite_modules_header,
            modifier = Modifier.padding(start = 10.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(mainHorizontalPadding / 2),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(favouriteModules) {
                var isLikedState by remember { mutableStateOf(it.isLiked) }

                SmallModuleCard(
                    moduleName = it.name,
                    isLiked = isLikedState,
                    onLikeClick = { isLikedState = !isLikedState }
                )
            }
        }

        Spacer(modifier = Modifier.height(mainHorizontalPadding))

        SubTitleText(
            textRes = R.string.all_modules_header,
            modifier = Modifier.padding(start = 10.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(mainHorizontalPadding / 2),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(allModules) {
                var isLikedState by remember { mutableStateOf(it.isLiked) }

                SmallModuleCard(
                    moduleName = it.name,
                    isLiked = isLikedState,
                    onLikeClick = { isLikedState = !isLikedState }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchModulesScreenPreview() = OdooMiemAndroidTheme {
    val modules = mutableListOf(
        OdooModule(
            name = "CRM",
            numberOfNotifications = 1
        ),
        OdooModule(
            name = "Recruitment",
            numberOfNotifications = 5
        ),
        OdooModule(
            name = "Pricing",
            numberOfNotifications = 123
        ),
    )

    SearchModulesScreen(
        allModules = modules,
        favouriteModules = modules
    )
}