package odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.components.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.mxalbert.sharedelements.FadeMode
import com.mxalbert.sharedelements.MaterialArcMotionFactory
import com.mxalbert.sharedelements.SharedElement
import com.mxalbert.sharedelements.SharedElementsTransitionSpec
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.common.uiKitComponents.utils.SharedElementConstants

@Composable
fun RecruitmentLikeScreenSearch(
    onSearchBarClicked: () -> Unit,
    @StringRes searchHintRes: Int,
) = Column {
    var searchInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    SharedElement(
        key = stringResource(R.string.recruitment_search_bar_key),
        screenKey = stringResource(R.string.recruitment_search_screen_key),
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
            enabled = false,
            modifier = Modifier.clickable { onSearchBarClicked() },
            stringRes = searchHintRes,
        )
    }
}
