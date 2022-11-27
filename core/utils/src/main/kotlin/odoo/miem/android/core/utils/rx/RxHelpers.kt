package odoo.miem.android.core.utils.rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import odoo.miem.android.core.utils.Result

// TODO Description
fun <T> Observable<T>.processing(
    subscribeScheduler: Scheduler = Schedulers.io(),
    observeScheduler: Scheduler = AndroidSchedulers.mainThread()
): Observable<T> = this.subscribeOn(subscribeScheduler).observeOn(observeScheduler)

fun emptyResultPublishSubject() = PublishSubject.create<Result>()

fun lazyEmptyResultPublishSubject(): Lazy<PublishSubject<Result>> = lazy { emptyResultPublishSubject() }