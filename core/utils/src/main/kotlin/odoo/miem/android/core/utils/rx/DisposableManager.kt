package odoo.miem.android.core.utils.rx

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

@Suppress("TooManyFunctions")
class DisposableManager<Channel : Any> {

    private val generalChannel = CompositeDisposable()
    private val channels = ChanneledDisposable<Channel>()

    fun registerDisposable(disposable: Disposable, channel: Channel?) {
        if (channel == null) {
            registerGeneralDisposable(disposable)
        } else {
            replaceDisposableInChannel(channel, disposable)
        }
    }

    fun dispose(disposable: Disposable, channel: Channel?) {
        if (channel != null) {
            channels.compareAndDispose(channel, disposable)
        } else {
            generalChannel.remove(disposable)
        }
    }

    fun registerGeneralDisposable(disposable: Disposable) {
        if (!disposable.isDisposed) {
            generalChannel.add(disposable)
        }
    }

    fun disposeGeneralChannel() {
        generalChannel.clear()
    }

    fun disposeChannelIf(condition: (Channel) -> Boolean) {
        channels.disposeIf(condition)
    }

    fun disposeAllChannels() {
        channels.disposeAll()
    }

    fun replaceDisposableInChannel(channel: Channel, disposable: Disposable?) {
        channels.replace(channel, disposable)
    }

    fun disposeChannel(channel: Channel) {
        channels.dispose(channel)
    }

    fun isChannelDisposed(channel: Channel) = channels.isDisposed(channel)

    fun isChannelActive(channel: Channel) = !isChannelDisposed(channel)

    fun disposeAll() {
        disposeGeneralChannel()
        disposeAllChannels()
    }
}
