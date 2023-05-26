package odoo.miem.android.common.uiKitComponents.screenTemplates.base.detailsLike.components.header

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooOnGray

@Composable
internal fun ColumnScope.ProfileHeader(
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
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(horizontal = mainHorizontalPadding),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(heightPadding))
    }

    majorSubtitle?.takeIf { it.isNotEmpty() }?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.titleSmall,
            textDecoration = TextDecoration.Underline,
            color = odooOnGray,
            modifier = Modifier.padding(horizontal = mainHorizontalPadding),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(heightPadding))
    }

    minorSubtitle?.takeIf { it.isNotEmpty() }?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.titleSmall,
            color = odooOnGray,
            modifier = Modifier.padding(horizontal = mainHorizontalPadding),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(heightPadding))
    }
}
