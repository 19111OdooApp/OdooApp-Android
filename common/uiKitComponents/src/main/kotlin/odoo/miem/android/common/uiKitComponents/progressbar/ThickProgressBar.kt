package odoo.miem.android.common.uiKitComponents.progressbar

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * ProgressIndicator for ODOO
 *
 * @author Alexander Lyutikov
 *
 * @param modifier modifier
 * @param progress float between 0.0 and 1.0, represents how filled the indicator is
 * @param backgroundColor color of empty progress
 * @param tintColor color of filled progress
 */
@Composable
fun ThickLinearProgressIndicator(
    modifier: Modifier,
    progress: Float,
    backgroundColor: Color = Color.LightGray,
    tintColor: Color = MaterialTheme.colorScheme.primary,
) = LinearProgressIndicator(
    progress,
    modifier
        .height(10.dp)
        .clip(CircleShape),
    tintColor,
    backgroundColor,
)
