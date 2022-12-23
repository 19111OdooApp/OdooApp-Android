package odoo.miem.android.core.utils.rx

import androidx.annotation.CheckResult
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.PublishSubject
import odoo.miem.android.core.utils.Result

/**
 * Just helpers for creating new instance of [PublishSubject]
 *
 * @author Vorozhtsov Mikhail
 */

fun <T> emptyResultPublishSubject() = PublishSubject.create<Result<T>>()

fun <T> lazyEmptyResultPublishSubject(): Lazy<PublishSubject<Result<T>>> =
    lazy { emptyResultPublishSubject() }

fun <T : Any> Consumer<T>.toFunctional(): (T) -> Unit = { accept(it) }

fun Runnable.toFunctional(): () -> Unit = { run() }

val Disposable.isNotDisposed
    get() = !isDisposed