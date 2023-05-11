package odoo.miem.android.common.uiKitComponents.progressbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.core.uiKitTheme.odooErrorPrimaryDark
import odoo.miem.android.core.uiKitTheme.odooPrimaryDark
import odoo.miem.android.core.uiKitTheme.odooPrimaryGray

/**
 * ProgressIndicator for ODOO
 *
 *
 * @param statusDivisionMap is map of status with their count
 * @param count is total count of application with different status
 * @param modifier modifier
 *
 * @author Vorozhtcov Mikhail
 */
@Composable
fun ThickLinearProgressIndicator(
    statusDivisionMap: Map<DeadlineStatus, Int>,
    count: Int,
    modifier: Modifier
) = Row(
    modifier = modifier
        .height(10.dp)
        .clip(CircleShape),
) {
    statusDivisionMap.forEach { (key, value) ->
        if (value != 0) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(value.toFloat() / count.toFloat())
                    .background(resolveStatusColor(key))
            )
        }
    }
}

private fun resolveStatusColor(status: DeadlineStatus) = when (status) {
    DeadlineStatus.NO_TASKS -> odooPrimaryGray
    DeadlineStatus.EXPIRED -> odooErrorPrimaryDark
    DeadlineStatus.ACTIVE -> odooPrimaryDark
}
