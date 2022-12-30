package odoo.miem.android.core.utils.rx

import io.reactivex.rxjava3.core.Scheduler

class PresentationSchedulers(
    val main: Scheduler,
    val timer: Scheduler,
    val localSingleThread: Scheduler,
    val worker: Scheduler
) {
    fun scheduleRunnableOnMain(action: Runnable) {
        main.scheduleDirect(action)
    }

    fun scheduleOnMain(action: () -> Unit) {
        main.scheduleDirect { action() }
    }
}
