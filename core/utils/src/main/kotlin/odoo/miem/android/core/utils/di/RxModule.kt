package odoo.miem.android.core.utils.di

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.utils.rx.PresentationSchedulers
import odoo.miem.android.core.utils.rx.schedule.LocalSingleThreadScheduler
import odoo.miem.android.core.utils.rx.schedule.MainScheduler
import odoo.miem.android.core.utils.rx.schedule.TimerScheduler
import odoo.miem.android.core.utils.rx.schedule.WorkerScheduler
import java.util.concurrent.Executors

@Module
object RxModule {

    @Provides
    fun provideMainScheduler(): MainScheduler {
        return MainScheduler(AndroidSchedulers.mainThread())
    }

    @Provides
    fun providesWorkerScheduler(): WorkerScheduler {
        return WorkerScheduler(Schedulers.io())
    }

    @Provides
    fun providesTimerScheduler(): TimerScheduler {
        return TimerScheduler(Schedulers.computation())
    }

    @Provides
    fun providesLocalSingleThreadScheduler(): LocalSingleThreadScheduler {
        return LocalSingleThreadScheduler(Schedulers.from(Executors.newSingleThreadExecutor()))
    }

    @Provides
    fun providePresentationSchedulers(
        mainScheduler: MainScheduler,
        timerScheduler: TimerScheduler,
        localSingleThreadScheduler: LocalSingleThreadScheduler,
        workerScheduler: WorkerScheduler
    ): PresentationSchedulers {
        return PresentationSchedulers(
            mainScheduler.scheduler,
            timerScheduler.scheduler,
            localSingleThreadScheduler.scheduler,
            workerScheduler.scheduler
        )
    }
}
