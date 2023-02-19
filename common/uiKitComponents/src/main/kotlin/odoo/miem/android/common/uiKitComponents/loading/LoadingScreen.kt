package odoo.miem.android.common.uiKitComponents.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import odoo.miem.android.common.uiKitComponents.spinner.DefaultCircleSpinner

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    loadingContent: (@Composable () -> Unit)? = null
) = Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    loadingContent?.let {
        it.invoke()
    } ?: DefaultCircleSpinner()
}
