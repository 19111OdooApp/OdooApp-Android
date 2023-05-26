package odoo.miem.android.common.uiKitComponents.screen.base.searchLike.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mxalbert.sharedelements.FadeMode
import com.mxalbert.sharedelements.MaterialContainerTransformSpec
import com.mxalbert.sharedelements.SharedElement
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.headers.CommonModuleHeader
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.common.uiKitComponents.utils.SharedElementConstants
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding

@Composable
internal fun BaseMainContent(
    sharedKey: String,
    sharedScreenKey: String,
    userName: String,
    title: String,
    @StringRes searchBarPlaceholder: Int,
    onSearchBarClick: () -> Unit,
    onUserIconClick: () -> Unit = {},
    mainListContent: @Composable (ColumnScope.() -> Unit),
) = Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    SimpleLogoAppBar()

    CommonModuleHeader(
        userName = userName,
        onUserIconClick = onUserIconClick
    )

    Spacer(modifier = Modifier.height(24.dp))

    TitleText(
        text = title,
        isLarge = false,
        modifier = Modifier
            .align(Alignment.Start)
            .padding(horizontal = mainHorizontalPadding)
    )

    Spacer(modifier = Modifier.height(10.dp))

    SharedElement(
        key = sharedKey,
        screenKey = sharedScreenKey,
        transitionSpec = MaterialContainerTransformSpec(
            durationMillis = SharedElementConstants.transitionDurationMills,
            fadeMode = FadeMode.Out
        )
    ) {
        SearchTextField(
            enabled = false,
            value = TextFieldValue(),
            stringRes = searchBarPlaceholder,
            modifier = Modifier.clickable { onSearchBarClick() }
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

    mainListContent()
}
