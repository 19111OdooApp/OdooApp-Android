package odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.charlex.compose.HtmlText
import odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.TextType
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooOnGray
import odoo.miem.android.core.uiKitTheme.odooPrimary

@Composable
internal fun TextPage(
    textType: TextType
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
) {
    TitleText(
        text = textType.topic,
        isLarge = false,
        color = odooPrimary,
    )

    if (textType.text?.isNotEmpty() == true) {
        Spacer(modifier = Modifier.height(32.dp))

        HtmlText(
            text = textType.text,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = odooOnGray,
            lineHeight = 20.sp,
        )
    } else {
        Spacer(modifier = Modifier.height(128.dp))

        SubtitleText(
            text = "Unfortunately, the user did not provide the information",
            color = odooPrimary,
            textAlign = TextAlign.Center
        )
    }
}
