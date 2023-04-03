package odoo.miem.android.core.utils.rx

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import odoo.miem.android.core.utils.state.LoadingResult
import odoo.miem.android.core.utils.state.NothingResult
import odoo.miem.android.core.utils.state.Result

/**
 * Just helpers for creating new instance of [PublishSubject]
 *
 * @author Vorozhtsov Mikhail
 */

fun <T> emptyResultPublishSubject() = PublishSubject.create<Result<T>>()

fun <T> emptyResultBehaviorSubject() = BehaviorSubject.create<Result<T>>()

fun <T> lazyEmptyResultPublishSubject(): Lazy<PublishSubject<Result<T>>> =
    lazy { emptyResultPublishSubject() }

fun <T> lazyEmptyResultBehaviorSubject(): Lazy<BehaviorSubject<Result<T>>> =
    lazy { emptyResultBehaviorSubject() }

fun <T : Any> Consumer<T>.toFunctional(): (T) -> Unit = { accept(it) }

fun Runnable.toFunctional(): () -> Unit = { run() }

val Disposable.isNotDisposed
    get() = !isDisposed

fun <T> Subject<Result<T>>.onLoadingState() {
    onNext(LoadingResult())
}

@Composable
fun <T> Subject<Result<T>>.collectAsState(defaultResult: Result<T> = NothingResult()) =
    this.subscribeAsState(
        defaultResult
    )
