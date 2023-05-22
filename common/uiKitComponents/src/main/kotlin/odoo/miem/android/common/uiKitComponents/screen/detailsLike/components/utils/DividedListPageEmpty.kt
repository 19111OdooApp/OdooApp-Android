package odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding

@Composable
fun DividedListPageEmpty() = Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = mainHorizontalPadding)
        .padding(top = commonPadding),
) {
    Icon(
        painter = painterResource(R.drawable.ic_info),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
    )

    Spacer(modifier = Modifier.height(mainVerticalPadding / 2))

    LabelText(
        textRes = R.string.details_empty_status,
        isLarge = true,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(mainVerticalPadding / 2))

    SubtitleText(
        textRes = R.string.details_empty_status_description,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.tertiary
    )
}
