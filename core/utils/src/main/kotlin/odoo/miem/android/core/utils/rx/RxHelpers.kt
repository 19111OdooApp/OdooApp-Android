package odoo.miem.android.core.utils.rx

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import io.reactivex.rxjava3.subjects.PublishSubject
import odoo.miem.android.core.utils.state.LoadingResult
import odoo.miem.android.core.utils.state.NothingResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSubject

/**
 * Just helpers for creating new instance of [PublishSubject]
 *
 * @author Vorozhtsov Mikhail
 */

fun <T> emptyResultPublishSubject() = PublishSubject.create<Result<T>>()

fun <T> lazyEmptyResultPublishSubject(): Lazy<PublishSubject<Result<T>>> = lazy { emptyResultPublishSubject() }

fun <T> ResultSubject<T>.onLoadingState() {
    onNext(LoadingResult())
}

@Composable
fun <T> ResultSubject<T>.collectAsState(defaultResult: Result<T> = NothingResult()) = this.subscribeAsState(
    defaultResult
)
