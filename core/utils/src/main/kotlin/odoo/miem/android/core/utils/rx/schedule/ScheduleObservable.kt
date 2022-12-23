package odoo.miem.android.core.utils.rx.schedule

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import odoo.miem.android.core.utils.rx.DisposableManager


fun <T : Any, Channel : Any> schedule(
    observable: Observable<T>,
    observeOnScheduler: Scheduler,
    subscribeOnScheduler: Scheduler,
    disposableManager: DisposableManager<Channel>,
    channel: Channel?,
    observer: Observer<T>,
    doFinally: (() -> Unit)? = null
) {
    observable.subscribeOn(subscribeOnScheduler)
        .observeOn(observeOnScheduler)
        .run {
            applyDoFinallyIfNeeded(
                rxTypeInstance = this,
                action = doFinally,
                callDoFinally = { doFinally(it) }
            )
        }
        .subscribe(
            WrapperObserver(
                disposableManager = disposableManager,
                channel = channel,
                downstream = observer
            )
        )
}

fun <T : Any, Channel : Any> schedule(
    observable: Observable<T>,
    observeOnScheduler: Scheduler,
    subscribeOnScheduler: Scheduler,
    disposableManager: DisposableManager<Channel>,
    channel: Channel? = null,
    onNext: ((T) -> Unit)? = null,
    onError: ((Throwable) -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
    onSubscribe: ((Disposable) -> Unit)? = null,
    doFinally: (() -> Unit)? = null
) {
    schedule(
        observable = observable,
        observeOnScheduler = observeOnScheduler,
        subscribeOnScheduler = subscribeOnScheduler,
        disposableManager = disposableManager,
        channel = channel,
        observer = LambdaObserver(
            onNext = onNext,
            onError = onError,
            onComplete = onComplete,
            onSubscribe = onSubscribe
        ),
        doFinally = doFinally
    )
}

private class LambdaObserver<T : Any>(
    private val onNext: ((T) -> Unit)?,
    private val onError: ((Throwable) -> Unit)?,
    private val onComplete: (() -> Unit)?,
    private val onSubscribe: ((Disposable) -> Unit)?
) : Observer<T> {

    override fun onComplete() {
        onComplete?.invoke()
    }

    override fun onSubscribe(d: Disposable) {
        onSubscribe?.invoke(d)
    }

    override fun onNext(t: T) {
        onNext?.invoke(t)
    }

    override fun onError(e: Throwable) {
        onError?.invoke(e)
    }
}

private class WrapperObserver<T : Any, Channel : Any>(
    disposableManager: DisposableManager<Channel>,
    channel: Channel?,
    private val downstream: Observer<T>
) : Observer<T>, ObserverContractHelper<Channel>(disposableManager, channel) {

    override fun onSubscribe(d: Disposable) {
        handleSubscribeEvent(d) {
            downstream.onSubscribe(it)
        }
    }

    override fun onNext(t: T) {
        handleIntermediateEvent(
            actualHandler = { downstream.onNext(t) },
            onErrorHandler = { downstream.onError(it) }
        )
    }

    override fun onComplete() {
        handleTerminalEvent {
            downstream.onComplete()
        }
    }

    override fun onError(t: Throwable) {
        handleErrorEvent(t) {
            downstream.onError(it)
        }
    }
}