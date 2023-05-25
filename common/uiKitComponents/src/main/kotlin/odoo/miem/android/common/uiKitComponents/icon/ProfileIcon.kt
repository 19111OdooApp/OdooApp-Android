package odoo.miem.android.common.uiKitComponents.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import odoo.miem.android.common.uiKitComponents.text.HeadlineText

@Composable
fun ProfileIcon(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    userName: String,
    iconSize: Dp = 40.dp,
) {
    val defaultIconSize = 40.dp

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(avatarUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(iconSize)
            .clip(CircleShape)
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator()
            }
            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
            }
            else -> {
                DefaultProfileIcon(
                    userName = userName,
                    iconSize = iconSize,
                    defaultIconSize = defaultIconSize,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
private fun DefaultProfileIcon(
    userName: String,
    iconSize: Dp,
    defaultIconSize: Dp,
    modifier: Modifier = Modifier,
) = Box(
    modifier = modifier
        .size(iconSize)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.primary),
    contentAlignment = Alignment.Center,
) {
    HeadlineText(
        text = userName.first().toString(),
        color = Color.White,
        modifier = Modifier.scale(iconSize / defaultIconSize)
    )
}
