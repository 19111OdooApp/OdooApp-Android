package odoo.miem.android.feature.profile.impl.components.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import odoo.miem.android.common.uiKitComponents.text.*
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooOnGray
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.feature.profile.impl.R
import odoo.miem.android.feature.profile.impl.data.User

@Composable
fun ApplicationSummaryPage(
    user: User
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxSize()
) {
    val paddingVertical = 128.dp

    TitleText(
        text = stringResource(R.string.title_application_summary),
        isLarge = false,
        color = odooPrimary,
    )


    if (user.applicationSummary.isNotEmpty()) {
        Spacer(modifier = Modifier.height(paddingVertical / 8))

        Text(
            text = user.applicationSummary,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = odooOnGray,
            lineHeight = 20.sp,
        )
    } else {
        Spacer(modifier = Modifier.height(paddingVertical))

        SubtitleText(
            text = "Unfortunately, the user did not provide the information",
            color = odooPrimary,
            textAlign = TextAlign.Center
        )
    }
}