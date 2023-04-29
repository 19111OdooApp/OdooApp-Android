package odoo.miem.android.common.uiKitComponents.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.spinner.DefaultCircleSpinner
import odoo.miem.android.common.uiKitComponents.utils.getBackgroundColorCard
import odoo.miem.android.common.uiKitComponents.utils.glowEffect

/**
 * [SmallModuleCard] is implementation of small module's card
 *
 * @param moduleName - name of module
 * @param isLiked - is module liked. If true, icon will be filled
 * @param onLikeClick - action, when user click on icon like
 * @param onClick - action, when user click on card
 * // TODO @param iconUrl
 *
 * @author Vorozhtsov Mikhail
 */
@Composable
fun SmallModuleCard(
    modifier: Modifier = Modifier,
    moduleName: String = stringResource(id = R.string.default_module_name),
    iconDownloadUrl: String,
    isLiked: Boolean = false,
    onLikeClick: () -> Unit = {},
    onClick: () -> Unit = {}
) = Box(
    modifier = modifier
        .fillMaxWidth()
        .height(120.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(moduleName.getBackgroundColorCard())
        .clickable { onClick() }
) {
    IconButton(
        onClick = onLikeClick,
        modifier = Modifier
            .size(42.dp)
            .padding(top = 14.dp, end = 14.dp)
            .align(Alignment.TopEnd)
    ) {
        Icon(
            imageVector = if (isLiked) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.glowEffect(isLiked)
        )
    }

    Box(
        modifier = Modifier
            .size(80.dp)
            .align(Alignment.Center),
        contentAlignment = Alignment.Center
    ) {
        if (iconDownloadUrl.isNotBlank()) {
            val model = ImageRequest.Builder(LocalContext.current)
                .data(iconDownloadUrl)
                .size(Size.ORIGINAL)
                .build()

            val painter = rememberAsyncImagePainter(model = model)

            if (painter.state is AsyncImagePainter.State.Success) {
                Icon(
                    painter = painter,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                DefaultCircleSpinner(
                    modifier = Modifier.size(36.dp)
                )
            }
        } else {
            Icon(
                imageVector = Icons.Rounded.QuestionMark,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }

    Text(
        text = moduleName,
        style = MaterialTheme.typography.titleSmall,
        color = Color.White,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(vertical = 10.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
