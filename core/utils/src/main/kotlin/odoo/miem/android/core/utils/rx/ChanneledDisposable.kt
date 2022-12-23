package odoo.miem.android.core.utils.rx

import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference

class ChanneledDisposable<T : Any> {

    private val channels = ConcurrentHashMap<T, AtomicReference<Disposable>>()

    fun replace(channelId: T, disposable: Disposable?) {
        if (disposable == null || disposable.isDisposed) {
            dispose(channelId)
        } else {
            channels.getOrPut(channelId) { AtomicReference() }
                .getAndSet(disposable)
                ?.dispose()
        }
    }

    fun dispose(channelId: T) {
        channels[channelId]?.getAndSet(Disposable.disposed())?.dispose()
    }

    fun isDisposed(channelId: T): Boolean {
        val disposable = channels[channelId]?.get()
        return disposable == null || disposable.isDisposed
    }

    fun disposeIf(condition: (T) -> Boolean) {
        channels.keys.forEach { if (condition(it)) dispose(it) }
    }

    fun disposeAll() {
        channels.keys.forEach { dispose(it) }
    }

    fun compareAndDispose(channelId: T, expected: Disposable) {
        channels[channelId]?.compareAndSet(expected, Disposable.disposed())
        expected.dispose()
    }
}