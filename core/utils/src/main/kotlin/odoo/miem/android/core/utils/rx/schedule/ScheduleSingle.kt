package odoo.miem.android.core.utils.rx.schedule

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import odoo.miem.android.core.utils.rx.DisposableManager

fun <T : Any, Channel : Any> schedule(
    single: Single<T>,
    observeOnScheduler: Scheduler,
    subscribeOnScheduler: Scheduler,
    disposableManager: DisposableManager<Channel>,
    channel: Channel?,
    observer: SingleObserver<T>,
    doFinally: (() -> Unit)? = null
) {
    single.subscribeOn(subscribeOnScheduler)
        .observeOn(observeOnScheduler)
        .run { applyDoFinallyIfNeeded(this, doFinally, { doFinally(it) }) }
        .subscribe(
            SingleWrapperObserver(
                disposableManager = disposableManager,
                channel = channel,
                downstream = observer
            )
        )
}

fun <T : Any, Channel : Any> schedule(
    single: Single<T>,
    observeOnScheduler: Scheduler,
    subscribeOnScheduler: Scheduler,
    disposableManager: DisposableManager<Channel>,
    channel: Channel? = null,
    onSuccess: ((T) -> Unit)? = null,
    onError: ((Throwable) -> Unit)? = null,
    onSubscribe: ((Disposable) -> Unit)? = null,
    doFinally: (() -> Unit)? = null
) {
    schedule(
        single = single,
        observeOnScheduler = observeOnScheduler,
        subscribeOnScheduler = subscribeOnScheduler,
        disposableManager = disposableManager,
        channel = channel,
        observer = SingleLambdaObserver(
            onSuccess = onSuccess,
            onError = onError,
            onSubscribe = onSubscribe
        ),
        doFinally = doFinally
    )
}

private class SingleLambdaObserver<T : Any>(
    private val onSuccess: ((T) -> Unit)?,
    private val onError: ((Throwable) -> Unit)?,
    private val onSubscribe: ((Disposable) -> Unit)?
) : SingleObserver<T> {

    override fun onSubscribe(d: Disposable) {
        onSubscribe?.invoke(d)
    }

    override fun onSuccess(t: T) {
        onSuccess?.invoke(t)
    }

    override fun onError(e: Throwable) {
        onError?.invoke(e)
    }
}

private class SingleWrapperObserver<T : Any, Channel : Any>(
    disposableManager: DisposableManager<Channel>,
    channel: Channel?,
    private val downstream: SingleObserver<T>
) : SingleObserver<T>, ObserverContractHelper<Channel>(disposableManager, channel) {

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

    override fun onError(e: Throwable) {
        handleErrorEvent(e) {
            downstream.onError(it)
        }
    }
}
