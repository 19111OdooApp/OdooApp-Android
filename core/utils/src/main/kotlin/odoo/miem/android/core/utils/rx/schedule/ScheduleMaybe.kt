package odoo.miem.android.core.utils.rx.schedule

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import odoo.miem.android.core.utils.rx.DisposableManager

fun <T : Any, Channel : Any> schedule(
    maybe: Maybe<T>,
    observeOnScheduler: Scheduler,
    subscribeOnScheduler: Scheduler,
    disposableManager: DisposableManager<Channel>,
    channel: Channel?,
    observer: MaybeObserver<T>,
    doFinally: (() -> Unit)? = null
) {
    maybe.subscribeOn(subscribeOnScheduler)
        .observeOn(observeOnScheduler)
        .run { applyDoFinallyIfNeeded(this, doFinally, { doFinally(it) }) }
        .subscribe(
            MaybeWrapperObserver(
                disposableManager = disposableManager,
                channel = channel,
                downstream = observer
            )
        )
}

fun <T : Any, Channel : Any> schedule(
    maybe: Maybe<T>,
    observeOnScheduler: Scheduler,
    subscribeOnScheduler: Scheduler,
    disposableManager: DisposableManager<Channel>,
    channel: Channel? = null,
    onSuccess: ((T) -> Unit)? = null,
    onError: ((Throwable) -> Unit)? = null,
    onSubscribe: ((Disposable) -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
    doFinally: (() -> Unit)? = null
) {
    schedule(
        maybe = maybe,
        observeOnScheduler = observeOnScheduler,
        subscribeOnScheduler = subscribeOnScheduler,
        disposableManager = disposableManager,
        channel = channel,
        observer = MaybeLambdaObserver(
            onSuccess = onSuccess,
            onError = onError,
            onComplete = onComplete,
            onSubscribe = onSubscribe
        ),
        doFinally = doFinally
    )
}

private class MaybeLambdaObserver<T : Any>(
    private val onSuccess: ((T) -> Unit)?,
    private val onError: ((Throwable) -> Unit)?,
    private val onComplete: (() -> Unit)?,
    private val onSubscribe: ((Disposable) -> Unit)?
) : MaybeObserver<T> {

    override fun onSubscribe(d: Disposable) {
        onSubscribe?.invoke(d)
    }

    override fun onSuccess(t: T) {
        onSuccess?.invoke(t)
    }

    override fun onComplete() {
        onComplete?.invoke()
    }

    override fun onError(e: Throwable) {
        onError?.invoke(e)
    }
}

private class MaybeWrapperObserver<T : Any, Channel : Any>(
    disposableManager: DisposableManager<Channel>,
    channel: Channel?,
    private val downstream: MaybeObserver<T>
) : MaybeObserver<T>, ObserverContractHelper<Channel>(disposableManager, channel) {

    override fun onSubscribe(d: Disposable) {
        handleSubscribeEvent(d) {
            downstream.onSubscribe(it)
        }
    }

    override fun onSuccess(t: T) {
        handleTerminalEvent {
            downstream.onSuccess(t)
        }
    }

    override fun onComplete() {
        handleTerminalEvent {
            downstream.onComplete()
        }
    }

    override fun onError(e: Throwable) {
        handleErrorEvent(e) {
            downstream.onError(it)
        }
    }
}
