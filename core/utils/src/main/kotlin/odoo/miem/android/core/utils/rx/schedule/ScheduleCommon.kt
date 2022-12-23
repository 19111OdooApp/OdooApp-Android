package odoo.miem.android.core.utils.rx.schedule

import androidx.annotation.CheckResult
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.exceptions.CompositeException
import io.reactivex.rxjava3.exceptions.Exceptions
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.internal.disposables.DisposableHelper
import io.reactivex.rxjava3.internal.util.EndConsumerHelper
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import odoo.miem.android.core.utils.rx.DisposableManager
import odoo.miem.android.core.utils.rx.isNotDisposed
import java.util.concurrent.atomic.AtomicReference

@CheckResult
inline fun <RxType : Any> applyDoFinallyIfNeeded(
    rxTypeInstance: RxType,
    noinline action: (() -> Unit)? = null,
    callDoFinally: RxType.(Action) -> RxType
): RxType {
    return if (action != null) {
        rxTypeInstance.callDoFinally(Action { action.invoke() })
    } else {
        rxTypeInstance
    }
}

abstract class ObserverContractHelper<Channel : Any>(
    private val disposableManager: DisposableManager<Channel>,
    private val channel: Channel?
) : Disposable {

    private val upstream = AtomicReference<Disposable>()

    @Suppress("TooGenericExceptionCaught")
    fun handleSubscribeEvent(disposable: Disposable, actualHandler: (Disposable) -> Unit) {
        try {
            if (EndConsumerHelper.setOnce(upstream, disposable, javaClass)) {
                disposableManager.registerDisposable(this, channel)
                actualHandler(this)
            }
        } catch (e: Throwable) {
            dispose()
            handleUncaughtError(e)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    fun handleIntermediateEvent(
        actualHandler: () -> Unit,
        onErrorHandler: (Throwable) -> Unit
    ) {
        if (isNotDisposed) {
            try {
                actualHandler()
            } catch (e: Throwable) {
                handleErrorEvent(e, onErrorHandler)
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    fun handleTerminalEvent(actualHandler: () -> Unit) {
        if (isNotDisposed) {
            dispose()
            try {
                actualHandler()
            } catch (e: Throwable) {
                handleUncaughtError(e)
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    fun handleErrorEvent(error: Throwable, actualHandler: (Throwable) -> Unit) {
        if (isNotDisposed) {
            dispose()
            try {
                actualHandler(error)
            } catch (innerError: Throwable) {
                handleUncaughtError(innerError) { CompositeException(error, it) }
            }
        } else {
            RxJavaPlugins.onError(error)
        }
    }

    @Suppress("ForbiddenComment")
    private fun handleUncaughtError(e: Throwable, mapPluginError: (Throwable) -> Throwable = { it }) {
        Watson.analyzeAndReportBug(e) // TODO: Добавить set в Watson
        Exceptions.throwIfFatal(e)
        RxJavaPlugins.onError(mapPluginError(e))
    }

    override fun isDisposed() = DisposableHelper.isDisposed(upstream.get())

    override fun dispose() {
        if (DisposableHelper.dispose(upstream)) {
            disposableManager.dispose(this, channel)
        }
    }
}
