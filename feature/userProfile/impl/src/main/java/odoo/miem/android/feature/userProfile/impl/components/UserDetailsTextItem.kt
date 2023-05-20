package odoo.miem.android.feature.userProfile.impl.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Suppress("MagicNumber")
@Composable
internal fun UserDetailsTextItem(
    key: String,
    value: String?
) = Row(
    modifier = Modifier.height(32.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
) {
    val commonPadding = 48.dp
    ItemText(
        text = key,
        modifier = Modifier.padding(end = commonPadding).weight(0.5F),
        textAlign = TextAlign.End
    )

    Divider(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
    )

    ItemText(
        text = value ?: "Empty",
        modifier = Modifier.padding(start = commonPadding).weight(0.5F),
        textAlign = TextAlign.Start
    )
}

@Composable
internal fun ItemText(
    text: String,
    modifier: Modifier,
    textAlign: TextAlign? = null
) = Text(
    text = text,
    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
    color = MaterialTheme.colorScheme.onPrimary,
    overflow = TextOverflow.Ellipsis,
    maxLines = 1,
    modifier = modifier,
    textAlign = textAlign
)
