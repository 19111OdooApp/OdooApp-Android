package odoo.miem.android.common.uiKitComponents.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import odoo.miem.android.common.uiKitComponents.text.HeadlineText


@Composable
fun ProfileIcon(
    avatarUrl: String? = null,
    userName: String,
    iconSize: Dp = 40.dp,
) {
    if (avatarUrl != null) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(avatarUrl)
                    .apply {
                        DefaultProfileIcon(userName, iconSize)
                    }
                    .build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(iconSize)
                .clip(CircleShape)
        )
    } else {
        DefaultProfileIcon(userName, iconSize)
    }
}

@Composable
private fun DefaultProfileIcon(
    userName: String,
    iconSize: Dp
) = Box(
    modifier = Modifier
        .size(iconSize)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.primary),
    contentAlignment = Alignment.Center,
) {
    HeadlineText(text = userName.first().toString(), color = Color.White)
}