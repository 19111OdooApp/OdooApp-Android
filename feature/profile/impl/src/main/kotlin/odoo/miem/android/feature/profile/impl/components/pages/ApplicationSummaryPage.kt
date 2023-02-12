package odoo.miem.android.feature.profile.impl.components.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooOnGray
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.feature.profile.impl.R

@Composable
fun ApplicationSummaryPage() = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxWidth()
) {
    TitleText(
        text = stringResource(R.string.title_application_summary),
        isLarge = false,
        color = odooPrimary
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus consequat est. Praesent viverra nisl felis, eget pharetra mauris bibendum ac. Sed justo orci, blandit vehicula vestibulum quis, interdum in eros. Sed a eros luctus risus pharetra consequat. Vivamus mollis a lectus quis elementum. Integer vel nibh at nulla faucibus consequat. Morbi placerat tortor ut orci mattis, ut dapibus risus porta. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras luctus tempus est ut malesuada.",
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
        color = odooOnGray,
        lineHeight = 20.sp,
    )
}