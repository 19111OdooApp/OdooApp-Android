package odoo.miem.android.common.uiKitComponents.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
internal fun Modifier.glowEffect(turned: Boolean) = if (turned) this.shadow(
    elevation = 12.dp,
    shape = MaterialTheme.shapes.extraLarge
) else this