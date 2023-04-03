package odoo.miem.android.common.uiKitComponents.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.utils.animatedLinearGradientBorder
import odoo.miem.android.core.uiKitTheme.odooPrimary

/**
 * Icon surrounded by gradient circle, used in [RecruitmentScreen]
 *
 * @author Alexander Lyutikov
 *
 * @param iconResource resource of the icon to be drawn
 * @param modifier modifier
 * @param colors gradient colors
 * @param height diameter of the circle
 * @param onClick action
 */
@Composable
fun GradientRoundButton(
    @DrawableRes iconResource: Int? = null,
    modifier: Modifier,
    colors: List<Color>,
    height: Float = 100f,
    onClick: () -> Unit = {},
) = IconButton(
    onClick = onClick,
    modifier = modifier
        .clip(CircleShape)
        .border(
            width = 2.dp,
            brush = animatedLinearGradientBorder(
                colors = colors,
                startX = 0.0f,
                startY = 0.0f,
                height = with(LocalDensity.current) {
                    height.dp.toPx()
                }
            ),
            shape = CircleShape
        )
) {
    val iconHeight = (height / 2).dp

    iconResource?.let { iconResource ->
        Icon(
            painter = painterResource(iconResource),
            contentDescription = null,
            Modifier.size(iconHeight)
        )
    } ?: Icon(
        imageVector = Icons.Default.Add,
        contentDescription = null,
        tint = odooPrimary,
        modifier = Modifier.size(iconHeight)
    )
}
