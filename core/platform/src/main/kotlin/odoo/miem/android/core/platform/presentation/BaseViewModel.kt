package odoo.miem.android.core.platform.presentation

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import odoo.miem.android.core.utils.rx.DisposableManager
import odoo.miem.android.core.utils.rx.PresentationSchedulers
import odoo.miem.android.core.utils.rx.schedule.schedule
import odoo.miem.android.core.utils.rx.toFunctional
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@Suppress("TooManyFunctions")
open class BaseViewModel(
    private val schedulers: PresentationSchedulers
) : ViewModel() {

    private val disposableManager = DisposableManager<Channel>()

    @JvmOverloads
    protected fun addDisposable(disposable: Disposable, channel: Channel? = null) {
        if (!disposable.isDisposed) {
            disposableManager.registerDisposable(disposable, channel)
        }
    }

    protected fun clearDisposables() {
        disposableManager.disposeGeneralChannel()
        disposableManager.disposeChannelIf { it.isDisposedOnDestroy }
    }

    protected fun Channel.replace(disposable: Disposable?) {
        disposableManager.replaceDisposableInChannel(this, disposable)
    }

    protected fun Channel.dispose() {
        disposableManager.disposeChannel(this)
    }

    protected val Channel.isDisposed: Boolean get() = disposableManager.isChannelDisposed(this)

    protected val Channel.isActive: Boolean get() = disposableManager.isChannelActive(this)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        clearDisposables()
    }

    protected fun <T : Any> Observable<T>.schedule(
        channel: Channel? = null,
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: (() -> Unit)? = null,
        onSubscribe: ((Disposable) -> Unit)? = null,
        doFinally: (() -> Unit)? = null,
        subscribeOnScheduler: Scheduler = schedulers.worker,
        observeOnScheduler: Scheduler = schedulers.main
    ) {
        schedule(
            observable = this,
            channel = channel,
            disposableManager = disposableManager,
            subscribeOnScheduler = subscribeOnScheduler,
            observeOnScheduler = observeOnScheduler,
            onNext = onNext,
            onError = onError,
            onComplete = onComplete,
            onSubscribe = onSubscribe,
            doFinally = doFinally
        )
    }

    protected fun <T : Any> Observable<T>.schedule(
        channel: Channel? = null,
        observer: Observer<T>,
        doFinally: (() -> Unit)? = null,
        subscribeOnScheduler: Scheduler = schedulers.worker,
        observeOnScheduler: Scheduler = schedulers.main
    ) {
        schedule(
            observable = this,
            subscribeOnScheduler = subscribeOnScheduler,
            observeOnScheduler = observeOnScheduler,
            disposableManager = disposableManager,
            channel = channel,
            observer = observer,
            doFinally = doFinally
        )
    }

    protected fun <T : Any> Single<T>.schedule(
        channel: Channel? = null,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onSubscribe: ((Disposable) -> Unit)? = null,
        doFinally: (() -> Unit)? = null,
        subscribeOnScheduler: Scheduler = schedulers.worker,
        observeOnScheduler: Scheduler = schedulers.main
    ) {
        return schedule(
            single = this,
            observeOnScheduler = observeOnScheduler,
            subscribeOnScheduler = subscribeOnScheduler,
            disposableManager = disposableManager,
            channel = channel,
            onSuccess = onSuccess,
            onError = onError,
            onSubscribe = onSubscribe,
            doFinally = doFinally
        )
    }

    protected fun <T : Any> Single<T>.schedule(
        channel: Channel? = null,
        observer: SingleObserver<T>,
        doFinally: (() -> Unit)? = null,
        subscribeOnScheduler: Scheduler = schedulers.worker,
        observeOnScheduler: Scheduler = schedulers.main
    ) {
        return schedule(
            single = this,
            observeOnScheduler = observeOnScheduler,
            subscribeOnScheduler = subscribeOnScheduler,
            disposableManager = disposableManager,
            channel = channel,
            observer = observer,
            doFinally = doFinally
        )
    }

    protected fun Completable.schedule(
        channel: Channel? = null,
        onComplete: (() -> Unit)? = null,
        onError: (Throwable) -> Unit,
        onSubscribe: ((Disposable) -> Unit)? = null,
        doFinally: (() -> Unit)? = null,
        observeOnScheduler: Scheduler = schedulers.main,
        subscribeOnScheduler: Scheduler = schedulers.worker
    ) {
        schedule(
            completable = this,
            observeOnScheduler = observeOnScheduler,
            subscribeOnScheduler = subscribeOnScheduler,
            disposableManager = disposableManager,
            channel = channel,
            onComplete = onComplete,
            onError = onError,
            onSubscribe = onSubscribe,
            doFinally = doFinally
        )
    }

    protected fun Completable.schedule(
        channel: Channel? = null,
        observer: CompletableObserver,
        doFinally: (() -> Unit)? = null,
        observeOnScheduler: Scheduler = schedulers.main,
        subscribeOnScheduler: Scheduler = schedulers.worker
    ) {
        schedule(
            completable = this,
            observeOnScheduler = observeOnScheduler,
            subscribeOnScheduler = subscribeOnScheduler,
            disposableManager = disposableManager,
            channel = channel,
            observer = observer,
            doFinally = doFinally
        )
    }

    protected fun <T : Any> Maybe<T>.schedule(
        channel: Channel? = null,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onSubscribe: ((Disposable) -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        doFinally: (() -> Unit)? = null,
        observeOnScheduler: Scheduler = schedulers.main,
        subscribeOnScheduler: Scheduler = schedulers.worker
    ) {
        return schedule(
            maybe = this,
            observeOnScheduler = observeOnScheduler,
            subscribeOnScheduler = subscribeOnScheduler,
            disposableManager = disposableManager,
            channel = channel,
            onSuccess = onSuccess,
            onError = onError,
            onSubscribe = onSubscribe,
            doFinally = doFinally,
            onComplete = onComplete
        )
    }

    protected fun <T : Any> Maybe<T>.schedule(
        channel: Channel? = null,
        observer: MaybeObserver<T>,
        doFinally: (() -> Unit)? = null,
        observeOnScheduler: Scheduler = schedulers.main,
        subscribeOnScheduler: Scheduler = schedulers.worker
    ) {
        return schedule(
            maybe = this,
            observeOnScheduler = observeOnScheduler,
            subscribeOnScheduler = subscribeOnScheduler,
            disposableManager = disposableManager,
            channel = channel,
            observer = observer,
            doFinally = doFinally
        )
    }

    protected fun <T : Any> Flowable<T>.schedule(
        channel: Channel? = null,
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: (() -> Unit)? = null,
        onSubscribe: ((Subscription) -> Unit)? = null,
        doFinally: (() -> Unit)? = null,
        observeOnScheduler: Scheduler = schedulers.main,
        subscribeOnScheduler: Scheduler = schedulers.worker
    ) {
        schedule(
            flowable = this,
            subscribeOnScheduler = subscribeOnScheduler,
            observeOnScheduler = observeOnScheduler,
            disposableManager = disposableManager,
            channel = channel,
            onNext = onNext,
            onError = onError,
            onComplete = onComplete,
            onSubscribe = onSubscribe,
            doFinally = doFinally
        )
    }

    protected fun <T : Any> Flowable<T>.schedule(
        channel: Channel? = null,
        subscriber: Subscriber<T>,
        doFinally: (() -> Unit)? = null,
        observeOnScheduler: Scheduler = schedulers.main,
        subscribeOnScheduler: Scheduler = schedulers.worker
    ) {
        schedule(
            flowable = this,
            subscribeOnScheduler = subscribeOnScheduler,
            observeOnScheduler = observeOnScheduler,
            disposableManager = disposableManager,
            channel = channel,
            subscriber = subscriber,
            doFinally = doFinally
        )
    }

    protected val javaFriendlyMethods = JavaFriendlyMethods()

    @Suppress("TooManyFunctions")
    inner class JavaFriendlyMethods {

        @JvmOverloads
        fun <T : Any> schedule(
            observable: Observable<T>,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>,
            channel: Channel? = null,
            doFinally: Runnable? = null,
            onComplete: Runnable? = null,
            onSubscribe: Consumer<Disposable>? = null
        ) {
            observable.schedule(
                channel = channel,
                onNext = onNext.toFunctional(),
                onError = onError.toFunctional(),
                onComplete = onComplete?.toFunctional(),
                onSubscribe = onSubscribe?.toFunctional(),
                doFinally = doFinally?.toFunctional()
            )
        }

        @JvmOverloads
        fun <T : Any> schedule(
            observable: Observable<T>,
            observer: Observer<T>,
            channel: Channel? = null,
            doFinally: Runnable? = null
        ) {
            observable.schedule(
                channel = channel,
                observer = observer,
                doFinally = doFinally?.toFunctional()
            )
        }

        @JvmOverloads
        fun <T : Any> schedule(
            single: Single<T>,
            onSuccess: Consumer<T>,
            onError: Consumer<Throwable>,
            channel: Channel? = null,
            doFinally: Runnable? = null,
            onSubscribe: Consumer<Disposable>? = null
        ) {
            single.schedule(
                channel = channel,
                onSuccess = onSuccess.toFunctional(),
                onError = onError.toFunctional(),
                onSubscribe = onSubscribe?.toFunctional(),
                doFinally = doFinally?.toFunctional()
            )
        }

        @JvmOverloads
        fun <T : Any> schedule(
            single: Single<T>,
            channel: Channel? = null,
            observer: SingleObserver<T>,
            doFinally: Runnable? = null
        ) {
            single.schedule(
                channel = channel,
                observer = observer,
                doFinally = doFinally?.toFunctional()
            )
        }

        @JvmOverloads
        fun schedule(
            completable: Completable,
            onComplete: Runnable,
            onError: Consumer<Throwable>,
            channel: Channel? = null,
            doFinally: Runnable? = null,
            onSubscribe: Consumer<Disposable>? = null
        ) {
            completable.schedule(
                channel = channel,
                onComplete = onComplete.toFunctional(),
                onError = onError.toFunctional(),
                onSubscribe = onSubscribe?.toFunctional(),
                doFinally = doFinally?.toFunctional()
            )
        }

        @JvmOverloads
        fun schedule(
            completable: Completable,
            channel: Channel? = null,
            observer: CompletableObserver,
            doFinally: Runnable? = null
        ) {
            completable.schedule(
                channel = channel,
                observer = observer,
                doFinally = doFinally?.toFunctional()
            )
        }

        @JvmOverloads
        fun <T : Any> schedule(
            maybe: Maybe<T>,
            onSuccess: Consumer<T>,
            onError: Consumer<Throwable>,
            channel: Channel? = null,
            doFinally: Runnable? = null,
            onComplete: Runnable? = null,
            onSubscribe: Consumer<Disposable>? = null
        ) {
            maybe.schedule(
                channel = channel,
                onSuccess = onSuccess.toFunctional(),
                onError = onError.toFunctional(),
                onSubscribe = onSubscribe?.toFunctional(),
                doFinally = doFinally?.toFunctional(),
                onComplete = onComplete?.toFunctional()
            )
        }

        @JvmOverloads
        fun <T : Any> schedule(
            maybe: Maybe<T>,
            channel: Channel? = null,
            observer: MaybeObserver<T>,
            doFinally: Runnable? = null
        ) {
            maybe.schedule(
                channel = channel,
                observer = observer,
                doFinally = doFinally?.toFunctional()
            )
        }

        @JvmOverloads
        fun <T : Any> schedule(
            flowable: Flowable<T>,
            onNext: Consumer<T>,
            onError: Consumer<Throwable>,
            channel: Channel? = null,
            doFinally: Runnable? = null,
            onComplete: Runnable? = null,
            onSubscribe: Consumer<Subscription>? = null
        ) {
            flowable.schedule(
                channel = channel,
                onNext = onNext.toFunctional(),
                onError = onError.toFunctional(),
                onComplete = onComplete?.toFunctional(),
                onSubscribe = onSubscribe?.toFunctional(),
                doFinally = doFinally?.toFunctional()
            )
        }

        @JvmOverloads
        fun <T : Any> schedule(
            flowable: Flowable<T>,
            channel: Channel? = null,
            subscriber: Subscriber<T>,
            doFinally: Runnable? = null
        ) {
            flowable.schedule(
                channel = channel,
                subscriber = subscriber,
                doFinally = doFinally?.toFunctional()
            )
        }

        @JvmOverloads
        fun replace(
            channel: Channel,
            disposable: Disposable?
        ) {
            channel.replace(disposable)
        }

        @JvmOverloads
        fun dispose(channel: Channel) {
            channel.dispose()
        }
    }

    data class Channel @JvmOverloads constructor(val isDisposedOnDestroy: Boolean = true) {

        override fun equals(other: Any?) = this === other

        override fun hashCode() = System.identityHashCode(this)
    }
}
