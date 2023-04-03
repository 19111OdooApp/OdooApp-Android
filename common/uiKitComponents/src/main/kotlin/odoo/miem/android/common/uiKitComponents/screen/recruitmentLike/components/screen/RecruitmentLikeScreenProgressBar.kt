package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import odoo.miem.android.common.uiKitComponents.progressbar.ThickLinearProgressIndicator
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding

private const val PROGRESS_BAR_TEXT_COEFFICIENT = 0.12f
private const val PROGRESS_BAR_COEFFICIENT = 0.88f

@Composable
public fun RecruitmentLikeScreenProgressBar(
    progress: Float,
    count: Int
) = Row(
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
) {
    ThickLinearProgressIndicator(
        progress = progress,
        modifier = Modifier
            .weight(PROGRESS_BAR_COEFFICIENT)
    )
    // Could break if someone has 100500 emoloyees
    SubtitleText(
        text = count.toString(),
        modifier = Modifier.weight(PROGRESS_BAR_TEXT_COEFFICIENT),
        textAlign = TextAlign.End
    )
}
