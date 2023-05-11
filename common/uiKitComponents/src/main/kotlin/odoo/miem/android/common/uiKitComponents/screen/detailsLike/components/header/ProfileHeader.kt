package odoo.miem.android.common.uiKitComponents.screen.detailsLike.components.header

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import odoo.miem.android.core.uiKitTheme.odooOnGray

@Composable
internal fun ProfileHeader(
    title: String?,
    majorSubtitle: String?,
    minorSubtitle: String?
) {
    val heightPadding = 8.dp

    title?.takeIf { it.isNotEmpty() }?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        Spacer(modifier = Modifier.height(heightPadding))
    }

    majorSubtitle?.takeIf { it.isNotEmpty() }?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.titleSmall,
            textDecoration = TextDecoration.Underline,
            color = odooOnGray
        )

        Spacer(modifier = Modifier.height(heightPadding))
    }

    minorSubtitle?.takeIf { it.isNotEmpty() }?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.titleSmall,
            color = odooOnGray
        )

        Spacer(modifier = Modifier.height(heightPadding))
    }
}
