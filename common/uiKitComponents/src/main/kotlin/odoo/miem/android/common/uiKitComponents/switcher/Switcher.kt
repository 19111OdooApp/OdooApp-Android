package odoo.miem.android.common.uiKitComponents.switcher

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.text.BodyText

@Composable
fun Switcher(
    firstStateText: String,
    secondStateText: String,
    isActiveFirst: Boolean = false,
    width: Dp = 200.dp,
    height: Dp = width / 2,
    padding: Dp = 10.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: (isActiveFirst: Boolean) -> Unit
) {
    val exactWidth = width - padding
    val offset by animateDpAsState(
        targetValue = if (isActiveFirst) 0.dp else exactWidth,
        animationSpec = animationSpec
    )

    Box(
        modifier = Modifier
            .width(width * 2)
            .height(height)
            .padding(horizontal = padding)
            .clip(parentShape)
            .clickable { onClick(isActiveFirst) }
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Box(
            modifier = Modifier
                .size(width = exactWidth, height = height)
                .offset(x = offset)
                .clip(shape = toggleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    shape = parentShape
                )
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SwitcherElement(
                text = firstStateText,
                width = exactWidth,
                height = height,
            )

            SwitcherElement(
                text = secondStateText,
                width = exactWidth,
                height = height,
            )
        }
    }
}

@Composable
private fun SwitcherElement(
    text: String,
    width: Dp,
    height: Dp,
) = Box(
    modifier = Modifier.size(width = width, height = height),
    contentAlignment = Alignment.Center
) {
    BodyText(
        text = text,
        isLarge = true,
        color = Color.White,
        textAlign = TextAlign.Center,
        maxLines = 1,
        modifier = Modifier
            .size(width = width, height = height)
            .wrapContentHeight(align = Alignment.CenterVertically)
    )
}
