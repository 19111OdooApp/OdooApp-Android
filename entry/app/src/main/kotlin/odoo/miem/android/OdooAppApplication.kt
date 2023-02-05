package odoo.miem.android

import android.app.Application
import android.os.StrictMode
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import odoo.miem.android.di.OdooAppComponent
import odoo.miem.android.di.initApis
import timber.log.Timber

/**
 * [OdooAppApplication] - application for initialization of DI Graph, [Timber] and enabling
 * Strict Mode
 *
 * @author Vorozhtsov Mikhail
 */
class OdooAppApplication : Application() {

    override fun onCreate() {
        initStrictMode()

        super.onCreate()

        initTimber()
        odooAppComponent = initApis(this)
        Timber.d("onCreate(): Init apis")

        setRxErrorHandler()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setRxErrorHandler() {
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                Timber.e(e.message)
            } else {
                Thread.currentThread().also { thread ->
                    thread.uncaughtExceptionHandler?.uncaughtException(thread, e)
                }
            }
        }
    }

    private fun initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectActivityLeaks()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDropBox()
                    .build()
            )

            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    // .detectDiskReads() // TODO Shared Preferences ruins, move to DataStore?
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyDropBox()
                    .penaltyLog()
                    .build()
            )
        }
    }

    companion object {
        lateinit var odooAppComponent: OdooAppComponent
    }
}
