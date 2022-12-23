package odoo.miem.android.common.uiKitComponents.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * [animatedGradientBorder] - function for creating Brush of card border
 *
 * @author Egor Danilov
 */
@Composable
fun animatedGradientBorder(
    colors: List<Color>,
    startX: Float,
    startY: Float,
    height: Float
): Brush {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = PI.toFloat(),
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing))
    )

    return Brush.linearGradient(
        colors = colors,
        start = Offset(startX, startY),
        end = Offset(height * cos(angle), height * sin(angle)),
        tileMode = TileMode.Mirror
    )
}
