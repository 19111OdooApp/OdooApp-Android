package odoo.miem.android.feature.selectingModules.impl.searchScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mxalbert.sharedelements.FadeMode
import com.mxalbert.sharedelements.MaterialArcMotionFactory
import com.mxalbert.sharedelements.SharedElement
import com.mxalbert.sharedelements.SharedElementsTransitionSpec
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.search.SearchResultEmpty
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.common.uiKitComponents.utils.SharedElementConstants
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.feature.selectingModules.impl.R
import odoo.miem.android.feature.selectingModules.impl.searchScreen.components.SearchRecommendationsContent
import odoo.miem.android.feature.selectingModules.impl.searchScreen.components.SearchResultContent

/**
 * [SearchModulesScreen] - screen for searching Odoo modules in [SelectingModulesScreen]
 *
 * @author Egor Danilov
 */
@Composable
fun SearchModulesScreen(
    allModules: List<OdooModule>,
    favouriteModules: List<OdooModule>,
    onSearchValueChange: (String) -> List<OdooModule>,
    onModuleCardClick: (OdooModule) -> Unit = {},
    onLikeModuleClick: (OdooModule) -> Unit = {},
    onBackPressed: () -> Unit = {},
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {
    var searchInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var filteredModules by rememberSaveable {
        mutableStateOf<List<OdooModule>>(emptyList())
    }
    val focusRequester = FocusRequester()

    BackHandler(enabled = true) {
        onBackPressed()
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SimpleLogoAppBar(onBackButtonClick = onBackPressed)

    Spacer(modifier = Modifier.height(40.dp))

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
            value = searchInput,
            onValueChange = {
                searchInput = it
                filteredModules = onSearchValueChange(it.text)
            },
            modifier = Modifier.focusRequester(focusRequester)
        )
    }

    Spacer(modifier = Modifier.height(mainVerticalPadding))

    AnimatedVisibility(
        visible = searchInput.text.isBlank(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        SearchRecommendationsContent(
            allModules = allModules,
            favouriteModules = favouriteModules,
            onModuleCardClick = onModuleCardClick,
            onLikeModuleClick = onLikeModuleClick
        )
    }

    AnimatedVisibility(
        visible = searchInput.text.isNotBlank(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        if (filteredModules.isEmpty()) {
            SearchResultEmpty(searchInput = searchInput.text)
        } else {
            SearchResultContent(
                filteredModules = filteredModules,
                onModuleCardClick = onModuleCardClick,
                onLikeModuleClick = onLikeModuleClick
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
private fun SearchModulesScreenPreview() = OdooMiemAndroidTheme {
    val modules = listOf(
        OdooModule(
            id = -1,
            parentId = null,
            childModules = mutableListOf(),
            name = "CRM",
            identificationName = "CRM",
            iconDownloadUrl = "",
        ),
        OdooModule(
            id = -1,
            parentId = null,
            childModules = mutableListOf(),
            name = "Recruitment",
            identificationName = "Recruitment",
            iconDownloadUrl = "",
            isFavourite = true
        ),
        OdooModule(
            id = -1,
            parentId = null,
            childModules = mutableListOf(),
            name = "Pricing",
            identificationName = "Pricing",
            iconDownloadUrl = "",
        ),
    )

    SearchModulesScreen(
        allModules = modules,
        favouriteModules = modules,
        onSearchValueChange = { emptyList() }
    )
}
