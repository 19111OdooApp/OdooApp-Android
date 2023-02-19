package odoo.miem.android.common.uiKitComponents.spinner

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCircleSpinner() = CircularProgressIndicator(
    color = MaterialTheme.colorScheme.primary,
    modifier = Modifier.size(48.dp)
)
