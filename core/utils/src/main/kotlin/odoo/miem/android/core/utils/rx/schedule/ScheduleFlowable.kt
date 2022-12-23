package odoo.miem.android.core.utils.rx.schedule

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.subscriptions.SubscriptionHelper
import io.reactivex.rxjava3.internal.util.EndConsumerHelper
import odoo.miem.android.core.utils.rx.DisposableManager
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.atomic.AtomicReference

fun <T : Any, Channel : Any> schedule(
    flowable: Flowable<T>,
    observeOnScheduler: Scheduler,
    subscribeOnScheduler: Scheduler,
    disposableManager: DisposableManager<Channel>,
    channel: Channel?,
    subscriber: Subscriber<T>,
    doFinally: (() -> Unit)? = null
) {
    flowable.subscribeOn(subscribeOnScheduler)
        .observeOn(observeOnScheduler)
        .run { applyDoFinallyIfNeeded(this, doFinally, { doFinally(it) }) }
        .subscribe(
            WrapperSubscriber(
                disposableManager = disposableManager,
                channel = channel,
                downstream = subscriber
            )
        )
}

fun <T : Any, Channel : Any> schedule(
    flowable: Flowable<T>,
    observeOnScheduler: Scheduler,
    subscribeOnScheduler: Scheduler,
    disposableManager: DisposableManager<Channel>,
    channel: Channel? = null,
    onNext: ((T) -> Unit)? = null,
    onError: ((Throwable) -> Unit)? = null,
    onComplete: (() -> Unit)? = null,
    onSubscribe: ((Subscription) -> Unit)? = null,
    doFinally: (() -> Unit)? = null
) {
    schedule(
        flowable = flowable,
        observeOnScheduler = observeOnScheduler,
        subscribeOnScheduler = subscribeOnScheduler,
        disposableManager = disposableManager,
        channel = channel,
        subscriber = LambdaSubscriber(
            onNext = onNext,
            onError = onError,
            onComplete = onComplete,
            onSubscribe = onSubscribe
        ),
        doFinally = doFinally
    )
}

private class WrapperSubscriber<T : Any, Channel : Any>(
    disposableManager: DisposableManager<Channel>,
    channel: Channel?,
    private val downstream: Subscriber<T>
) : Subscriber<T>, Subscription, ObserverContractHelper<Channel>(disposableManager, channel) {

    private val upstream = AtomicReference<Subscription?>()

    override fun onSubscribe(s: Subscription) {
        handleSubscribeEvent(Disposable.fromSubscription(s)) {
            if (EndConsumerHelper.setOnce(upstream, s, javaClass)) {
                downstream.onSubscribe(this)
            }
        }
    }

    override fun onNext(value: T) {
        handleIntermediateEvent(
            actualHandler = { downstream.onNext(value) },
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

    override fun cancel() {
        dispose()
    }

    override fun dispose() {
        super.dispose()
        SubscriptionHelper.cancel(upstream)
    }

    override fun request(n: Long) {
        val upstream = this.upstream.get()
        check(upstream != null) {
            "Метод onSubscribe ещё не был вызван!"
        }
        upstream.request(n)
    }
}

private class LambdaSubscriber<T : Any>(
    private val onNext: ((T) -> Unit)?,
    private val onError: ((Throwable) -> Unit)?,
    private val onComplete: (() -> Unit)?,
    private val onSubscribe: ((Subscription) -> Unit)?
) : Subscriber<T> {

    override fun onComplete() {
        onComplete?.invoke()
    }

    override fun onSubscribe(s: Subscription) {
        onSubscribe?.invoke(s)
    }

    override fun onNext(t: T) {
        onNext?.invoke(t)
    }

    override fun onError(t: Throwable) {
        onError?.invoke(t)
    }
}