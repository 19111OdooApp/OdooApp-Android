package odoo.miem.android.common.uiKitComponents.stateholder

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import odoo.miem.android.common.uiKitComponents.loading.LoadingScreen
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.LoadingResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.SuccessResult

@Composable
fun <T> StateHolder(
    state: Result<T>,
    loadingContent: (@Composable () -> Unit)? = null,
    errorContent: (@Composable (state: ErrorResult<T>) -> Unit)? = null,
    successContent: (@Composable (state: SuccessResult<T>) -> Unit)? = null
) = Crossfade(
    targetState = state
) {
    when (it) {
        is LoadingResult -> LoadingScreen(loadingContent = loadingContent)
        is SuccessResult -> successContent?.invoke(it)
        is ErrorResult -> errorContent?.invoke(it)
        else -> LoadingScreen(loadingContent = loadingContent)
    }
}
