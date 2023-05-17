package odoo.miem.android.common.uiKitComponents.screen.searchLike.components

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mxalbert.sharedelements.FadeMode
import com.mxalbert.sharedelements.MaterialArcMotionFactory
import com.mxalbert.sharedelements.SharedElement
import com.mxalbert.sharedelements.SharedElementsTransitionSpec
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.screen.searchLike.model.SearchLikeModel
import odoo.miem.android.common.uiKitComponents.search.SearchResultEmpty
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.common.uiKitComponents.utils.SharedElementConstants
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding

@Composable
internal fun <T : SearchLikeModel> BaseSearchingContent(
    sharedKey: String,
    sharedScreenKey: String,
    items: List<T>,
    @StringRes searchBarPlaceholder: Int,
    onBackPressed: () -> Unit = {},
    searchResultListContent: @Composable (ColumnScope.(items: List<T>) -> Unit),
    searchStartListContent: @Composable (ColumnScope.(items: List<T>) -> Unit)?
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {
    var searchInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    val filteredItems = items.filter {
        it.name.lowercase().contains(searchInput.text.lowercase())
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
        key = sharedKey,
        screenKey = sharedScreenKey,
        transitionSpec = SharedElementsTransitionSpec(
            pathMotionFactory = MaterialArcMotionFactory,
            durationMillis = SharedElementConstants.transitionDurationMills,
            fadeMode = FadeMode.Through,
            fadeProgressThresholds = SharedElementConstants.progressThreshold
        )
    ) {
        SearchTextField(
            value = searchInput,
            onValueChange = { searchInput = it },
            stringRes = searchBarPlaceholder,
            modifier = Modifier.focusRequester(focusRequester)
        )
    }

    Spacer(modifier = Modifier.height(mainVerticalPadding))

    AnimatedVisibility(
        visible = searchInput.text.isBlank(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        searchStartListContent?.let {
            it(items)
        } ?: searchResultListContent(items)
    }

    AnimatedVisibility(
        visible = searchInput.text.isNotBlank(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        if (filteredItems.isEmpty()) {
            SearchResultEmpty(searchInput = searchInput.text)
        } else {
            searchResultListContent(
                items = filteredItems
            )
        }
    }
}
