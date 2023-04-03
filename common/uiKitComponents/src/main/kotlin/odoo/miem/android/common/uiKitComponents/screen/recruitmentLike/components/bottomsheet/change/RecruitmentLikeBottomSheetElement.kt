package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.bottomsheet.change

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.text.BodyText
import odoo.miem.android.core.uiKitTheme.commonPadding

/**
 * Element of the bottom sheet of recruitment like screen
 *
 * @author Alexander Lyutikov
 *
 * @param onClick
 * @param painter image to draw
 * @param name subtitle to button
 * @param imageSize
 */
@Composable
fun RecruitmentLikeBottomSheetElement(
    onClick: () -> Unit,
    painter: Painter,
    name: String,
    imageSize: Dp = 50.dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Box(modifier = Modifier.size(50.dp)) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center)
                    .size(imageSize)
            )
        }

        Spacer(modifier = Modifier.padding(top = commonPadding))

        BodyText(
            text = name,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun RecruitmentBottomSheetElementPreview() {
    RecruitmentLikeBottomSheetElement(
        onClick = {},
        painter = painterResource(id = R.drawable.add_plus),
        name = "stringResource(id = R.string.recruitment_add_new_status)",
        imageSize = 30.dp
    )
}
