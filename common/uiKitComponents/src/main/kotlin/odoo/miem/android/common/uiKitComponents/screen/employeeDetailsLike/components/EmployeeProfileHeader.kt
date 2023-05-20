package odoo.miem.android.common.uiKitComponents.screen.employeeDetailsLike.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import odoo.miem.android.common.uiKitComponents.spinner.DefaultCircleSpinner
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.core.uiKitTheme.odooGray
import odoo.miem.android.core.uiKitTheme.odooOnGray

@Composable
internal fun EmployeeProfileHeader(
    id: Long?,
    name: String?,
    email: String?,
    mobilePhone: String?,
    workPhone: String?,
    company: String?,
    avatarLink: String?
) {
    val heightPadding = 8.dp

    val avatarSize = 108.dp
    val avatarShape = RoundedCornerShape(15.dp)

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = mainHorizontalPadding,
                vertical = commonPadding
            )
    ) {
        Box(
            modifier = Modifier.size(avatarSize)
        ) {
            if (avatarLink != null) {
                val model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarLink)
                    .size(Size.ORIGINAL)
                    .diskCacheKey("employee_avatar_$id")
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .allowHardware(true)
                    .crossfade(true)
                    .build()

                val painter = rememberAsyncImagePainter(model = model)

                when (painter.state) {
                    is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(avatarShape)
                                .size(avatarSize)
                        )
                    }
                    is AsyncImagePainter.State.Error -> {
                        Icon(
                            imageVector = Icons.Rounded.QuestionMark,
                            contentDescription = null,
                            tint = odooGray,
                            modifier = Modifier
                                .clip(avatarShape)
                                .size(avatarSize)
                        )
                    }
                    else -> {
                        DefaultCircleSpinner(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(36.dp)
                        )
                    }
                }
            } else {
                Icon(
                    imageVector = Icons.Rounded.QuestionMark,
                    contentDescription = null,
                    tint = odooGray,
                    modifier = Modifier
                        .clip(avatarShape)
                        .size(avatarSize)
                )
            }
        }

        Spacer(modifier = Modifier.width(mainVerticalPadding))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(heightPadding))
            }

            email?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    textDecoration = TextDecoration.Underline,
                    color = odooOnGray
                )

                Spacer(modifier = Modifier.height(heightPadding))
            }

            mobilePhone?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    color = odooOnGray
                )

                Spacer(modifier = Modifier.height(heightPadding))
            }

            workPhone?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    color = odooOnGray
                )

                Spacer(modifier = Modifier.height(heightPadding))
            }

            company?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    color = odooOnGray
                )

                Spacer(modifier = Modifier.height(heightPadding))
            }
        }
    }
}
