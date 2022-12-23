package odoo.miem.android.core.utils.rx.schedule

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import odoo.miem.android.core.utils.rx.DisposableManager


fun <Channel : Any> schedule(
    completable: Completable,
    observeOnScheduler: Scheduler,
    subscribeOnScheduler: Scheduler,
    disposableManager: DisposableManager<Channel>,
    channel: Channel?,
    observer: CompletableObserver,
    doFinally: (() -> Unit)? = null
) {
    completable.subscribeOn(subscribeOnScheduler)
        .observeOn(observeOnScheduler)
        .run { applyDoFinallyIfNeeded(this, doFinally, { doFinally(it) }) }
        .subscribe(
            CompletableWrapperObserver(
                disposableManager = disposableManager,
                channel = channel,
                downstream = observer
            )
        )
}

fun <C : Any> schedule(
    completable: Completable,
    observeOnScheduler: Scheduler,
    subscribeOnScheduler: Scheduler,
    disposableManager: DisposableManager<C>,
    channel: C? = null,
    onComplete: (() -> Unit)? = null,
    onError: ((Throwable) -> Unit)? = null,
    onSubscribe: ((Disposable) -> Unit)? = null,
    doFinally: (() -> Unit)? = null
) {
    schedule(
        completable = completable,
        observeOnScheduler = observeOnScheduler,
        subscribeOnScheduler = subscribeOnScheduler,
        disposableManager = disposableManager,
        channel = channel,
        observer = CompletableLambdaObserver(
            onComplete = onComplete,
            onError = onError,
            onSubscribe = onSubscribe
        ),
        doFinally = doFinally
    )
}

private class CompletableLambdaObserver(
    private val onComplete: (() -> Unit)?,
    private val onError: ((Throwable) -> Unit)?,
    private val onSubscribe: ((Disposable) -> Unit)?
) : CompletableObserver {

    override fun onSubscribe(d: Disposable) {
        onSubscribe?.invoke(d)
    }

    override fun onComplete() {
        onComplete?.invoke()
    }

    override fun onError(e: Throwable) {
        onError?.invoke(e)
    }
}

private class CompletableWrapperObserver<Channel : Any>(
    disposableManager: DisposableManager<Channel>,
    channel: Channel?,
    private val downstream: CompletableObserver
) : CompletableObserver, ObserverContractHelper<Channel>(disposableManager, channel) {

    override fun onSubscribe(d: Disposable) {
        handleSubscribeEvent(d) {
            downstream.onSubscribe(it)
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