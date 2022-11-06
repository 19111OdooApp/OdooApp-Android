package odoo.miem.android

import android.app.Application
import android.os.StrictMode
import odoo.miem.android.core.di.api.Api
import odoo.miem.android.di.OdooAppComponent
import odoo.miem.android.di.initApis
import timber.log.Timber

/**
 * [OdooAppApplication] - application для инициализации di графа, [Timber] и
 * включение Strict Mode
 *
 * @author Ворожцов Михаил
 */
class OdooAppApplication : Application() {

    override fun onCreate() {
        initStrictMode()

        super.onCreate()

        initTimber()
        odooAppComponent = initApis(this)
        Timber.d("onCreate(): Init apis")
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
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
                    .penaltyDeath()
                    .build()
            )

            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
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
