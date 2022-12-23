package odoo.miem.android.core.utils.rx.schedule

import io.reactivex.rxjava3.core.Scheduler

@SuppressWarnings("UnnecessaryAbstractClass")
public abstract class SchedulerWrapper(val scheduler: Scheduler)

public class MainScheduler(scheduler: Scheduler) : SchedulerWrapper(scheduler)

public class WorkerScheduler(scheduler: Scheduler) : SchedulerWrapper(scheduler)

public class NetworkingScheduler(scheduler: Scheduler) : SchedulerWrapper(scheduler)

public class TimerScheduler(scheduler: Scheduler) : SchedulerWrapper(scheduler)

public class LocalSingleThreadScheduler(scheduler: Scheduler) : SchedulerWrapper(scheduler)
