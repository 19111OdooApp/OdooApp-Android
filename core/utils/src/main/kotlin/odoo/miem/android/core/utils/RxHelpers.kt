package odoo.miem.android.core.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Observable<T>.processing(
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeScheduler: Scheduler = AndroidSchedulers.mainThread()
): Observable<T> = this.subscribeOn(subscribeScheduler).observeOn(observeScheduler)