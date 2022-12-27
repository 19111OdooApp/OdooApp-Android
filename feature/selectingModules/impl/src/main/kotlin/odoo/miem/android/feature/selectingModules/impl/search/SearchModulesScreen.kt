package odoo.miem.android.feature.selectingModules.impl.search

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.cards.BigModuleCard
import odoo.miem.android.common.uiKitComponents.cards.SmallModuleCard
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.core.sharedElements.FadeMode
import odoo.miem.android.core.sharedElements.SharedElement
import odoo.miem.android.core.sharedElements.base.MaterialArcMotionFactory
import odoo.miem.android.core.sharedElements.utils.SharedElementConstants
import odoo.miem.android.core.sharedElements.utils.SharedElementsTransitionSpec
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.feature.selectingModules.impl.R
import odoo.miem.android.feature.selectingModules.impl.data.OdooModule

/**
 * [SearchModulesScreen] - screen for searching Odoo modules in [SelectingModulesScreen]
 *
 * @author Egor Danilov
 */
@Composable
fun SearchModulesScreen(
    allModules: List<OdooModule>,
    favouriteModules: List<OdooModule>,
    searchInputState: TextFieldValue = TextFieldValue(),
    onValueChange: (TextFieldValue) -> Unit = {},
    onBackPressed: () -> Unit = {},
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {
    var isSearchBarEmpty by remember { mutableStateOf(true) }
    val focusRequester = FocusRequester()

    BackHandler(enabled = true) {
        onBackPressed()
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SimpleLogoAppBar()

    Spacer(modifier = Modifier.height(39.dp))

    SharedElement(
        key = stringResource(R.string.search_bar_key),
        screenKey = stringResource(R.string.search_screen_key),
        transitionSpec = SharedElementsTransitionSpec(
            pathMotionFactory = MaterialArcMotionFactory,
            durationMillis = SharedElementConstants.transitionDurationMills,
            fadeMode = FadeMode.Through,
            fadeProgressThresholds = SharedElementConstants.progressThreshold
        )
    ) {
        SearchTextField(
            value = searchInputState,
            onValueChange = {
                onValueChange(it)
                isSearchBarEmpty = it.text.isBlank()
                // TODO search logic from viewModel
            },
            modifier = Modifier.focusRequester(focusRequester)
        )
    }

    Spacer(modifier = Modifier.height(mainVerticalPadding))

    AnimatedVisibility(
        visible = isSearchBarEmpty,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        SearchRecommendationsContent(
            allModules = allModules,
            favouriteModules = favouriteModules
        )
    }

    AnimatedVisibility(
        visible = !isSearchBarEmpty,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        SearchResultContent(filteredModules = emptyList())
    }
}

/**
 * [SearchRecommendationsContent] is located in [SearchModulesScreen]
 * Looks like two lazy rows with favourite and all modules
 * Disappears when the search began
 *
 * @author Egor Danilov
 */
@Composable
private fun SearchRecommendationsContent(
    allModules: List<OdooModule>,
    favouriteModules: List<OdooModule>,
) {
    val itemSpacing = mainHorizontalPadding / 2
    val startRowPadding = mainHorizontalPadding / 2
    val endRowPadding = mainHorizontalPadding / 4

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (favouriteModules.isNotEmpty()) {
            LabelText(
                textRes = R.string.favourite_modules_header,
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
                    Spacer(modifier = Modifier.width(endRowPadding))
                }
            }
        }

        if (allModules.isNotEmpty()) {
            Spacer(modifier = Modifier.height(mainVerticalPadding))

            LabelText(
                textRes = R.string.all_modules_header,
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
                    Spacer(modifier = Modifier.width(endRowPadding))
                }
            }
        }
    }
}

/**
 * [SearchResultContent] - search results for [SearchModulesScreen]
 * If result is empty, shows relevant text and sad picture :(
 * Otherwise, shows column with [BigModuleCard] items
 *
 * @author Egor Danilov
 */
@Composable
private fun SearchResultContent(
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

            Image(
                painter = painterResource(R.drawable.ic_sad_smile),
                contentDescription = null,
                modifier = Modifier.padding(top = mainVerticalPadding)
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
