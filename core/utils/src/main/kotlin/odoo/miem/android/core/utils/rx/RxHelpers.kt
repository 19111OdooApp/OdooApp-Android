package odoo.miem.android.core.utils.rx

import io.reactivex.rxjava3.subjects.PublishSubject
import odoo.miem.android.core.utils.Result

/**
 * Just helpers for creating new instance of [PublishSubject]
 *
 * @author Vorozhtsov Mikhail
 */

fun <T> emptyResultPublishSubject() = PublishSubject.create<Result<T>>()

fun <T> lazyEmptyResultPublishSubject(): Lazy<PublishSubject<Result<T>>> = lazy { emptyResultPublishSubject() }
