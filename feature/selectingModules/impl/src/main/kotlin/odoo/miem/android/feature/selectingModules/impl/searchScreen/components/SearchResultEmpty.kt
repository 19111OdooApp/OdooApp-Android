package odoo.miem.android.feature.selectingModules.impl.searchScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.feature.selectingModules.impl.R
import odoo.miem.android.feature.selectingModules.impl.searchScreen.SearchModulesScreen

/**
 * [SearchResultEmpty] - empty search results for [SearchModulesScreen]
 * shows icon and apologies
 *
 * @author Egor Danilov
 */
@Composable
fun SearchResultEmpty(
    searchInput: String
) = Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = mainHorizontalPadding)
        .padding(top = commonPadding)
) {
    Icon(
        painter = painterResource(R.drawable.ic_info),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
    )

    Spacer(modifier = Modifier.height(mainVerticalPadding / 2))

    LabelText(
        textRes = R.string.search_result_empty,
        isLarge = true,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(mainVerticalPadding / 2))

    SubtitleText(
        text = stringResource(R.string.search_result_empty_desc)
            .format(searchInput),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.tertiary
    )
}
