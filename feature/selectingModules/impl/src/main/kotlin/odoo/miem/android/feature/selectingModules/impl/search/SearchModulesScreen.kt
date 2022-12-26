package odoo.miem.android.feature.selectingModules.impl.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import odoo.miem.android.core.sharedElements.FadeMode
import odoo.miem.android.core.sharedElements.SharedElement
import odoo.miem.android.core.sharedElements.base.MaterialArcMotionFactory
import odoo.miem.android.core.sharedElements.utils.ProgressThresholds
import odoo.miem.android.core.sharedElements.utils.SharedElementsTransitionSpec
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.feature.selectingModules.impl.R
import odoo.miem.android.feature.selectingModules.impl.data.OdooModule

@Composable
fun SearchModulesScreen(
    allModules: List<OdooModule>,
    favouriteModules: List<OdooModule>,
    searchInputState: TextFieldValue = TextFieldValue(),
    onValueChange: (TextFieldValue) -> Unit = {},
    onExit: () -> Unit = {},
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {

    SimpleLogoAppBar()

    Spacer(modifier = Modifier.height(39.dp))

    SharedElement(
        key = "searchBar",
        screenKey = "searchScreen",
        transitionSpec = SharedElementsTransitionSpec(
            pathMotionFactory = MaterialArcMotionFactory,
            durationMillis = 1000,
            fadeMode = FadeMode.Through,
            fadeProgressThresholds = ProgressThresholds(0.10f, 0.40f)
        )
    ) {
        SearchTextField(
            value = searchInputState,
            onValueChange = {
                onValueChange(it)
                // TODO search logic from viewModel
            }
        )
    }

    val mainVerticalPadding = 24.dp

    Spacer(modifier = Modifier.height(mainVerticalPadding))

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        SubTitleText(
            textRes = R.string.favourite_modules_header,
            modifier = Modifier.padding(start = 34.dp)
        )

        Spacer(modifier = Modifier.height(mainVerticalPadding))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(mainHorizontalPadding / 2),
            modifier = Modifier
                .fillMaxWidth()
//                .weight(4f)
        ) {
            item {
                Spacer(modifier = Modifier.width(mainHorizontalPadding / 2))
            }

            items(favouriteModules) {
                var isLikedState by remember { mutableStateOf(it.isLiked) }

                SmallModuleCard(
                    moduleName = it.name,
                    isLiked = isLikedState,
                    onLikeClick = { isLikedState = !isLikedState },
                    modifier = Modifier.width(170.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.width(mainHorizontalPadding / 4))
            }
        }

        Spacer(modifier = Modifier.height(mainVerticalPadding))

        SubTitleText(
            textRes = R.string.all_modules_header,
            modifier = Modifier.padding(start = 34.dp)
        )

        Spacer(modifier = Modifier.height(mainVerticalPadding))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(mainHorizontalPadding / 2),
            modifier = Modifier
                .fillMaxWidth()
//                .weight(3f)
        ) {
            item {
                Spacer(modifier = Modifier.width(mainHorizontalPadding / 2))
            }

            items(allModules) {
                var isLikedState by remember { mutableStateOf(it.isLiked) }

                SmallModuleCard(
                    moduleName = it.name,
                    isLiked = isLikedState,
                    onLikeClick = { isLikedState = !isLikedState },
                    modifier = Modifier.width(170.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.width(mainHorizontalPadding / 4))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
private fun SearchModulesScreenPreview() = OdooMiemAndroidTheme {
    val modules = listOf(
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