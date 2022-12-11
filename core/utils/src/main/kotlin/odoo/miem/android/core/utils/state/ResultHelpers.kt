package odoo.miem.android.core.utils.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
inline fun Result<*>.subscribeOnError(crossinline onError: (message: Int) -> Unit) = (this as? ErrorResult)?.message?.let {
    LaunchedEffect(this) {
        onError(it)
    }
}