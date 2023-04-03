package odoo.miem.android.common.uiKitComponents.progressbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// TODO: Migrate to another Loading screen
@Composable
fun LoadingScreen(
    load: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
    load()
}
