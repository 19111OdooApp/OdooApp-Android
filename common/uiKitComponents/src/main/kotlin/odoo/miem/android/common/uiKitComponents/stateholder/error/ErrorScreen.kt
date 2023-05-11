package odoo.miem.android.common.uiKitComponents.stateholder.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.buttons.FilledTextButton
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.core.uiKitTheme.odooErrorPrimaryDark

@Composable
fun ErrorScreen(
    onRetry: () -> Unit
) = Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = mainHorizontalPadding)
        .padding(top = commonPadding)
) {
    Icon(
        painter = painterResource(R.drawable.ic_info),
        contentDescription = null,
        tint = odooErrorPrimaryDark,
    )

    Spacer(modifier = Modifier.height(mainVerticalPadding))

    LabelText(
        textRes = R.string.error_message,
        isLarge = true,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(mainVerticalPadding / 2))

    SubtitleText(
        textRes = R.string.error_message_subtitle,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.tertiary
    )

    Spacer(modifier = Modifier.height(mainVerticalPadding))

    FilledTextButton(
        onClick = { onRetry() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 75.dp)
            .padding(horizontal = 36.dp),
        textResource = R.string.error_retry
    )
}
