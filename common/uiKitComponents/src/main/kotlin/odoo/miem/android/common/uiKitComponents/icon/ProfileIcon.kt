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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.*
import coil.request.ImageRequest
import odoo.miem.android.common.uiKitComponents.text.HeadlineText


@Composable
fun ProfileIcon(
    avatarUrl: String? = null,
    userName: String,
    iconSize: Dp = 40.dp,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = avatarUrl,
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
                DefaultProfileIcon(userName, iconSize, modifier)
            }
        }
    }
}

@Composable
private fun DefaultProfileIcon(
    userName: String,
    iconSize: Dp,
    modifier: Modifier
) = Box(
    modifier = modifier
        .size(iconSize)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.primary),
    contentAlignment = Alignment.Center,
) {
    HeadlineText(text = userName.first().toString(), color = Color.White)
}