package odoo.miem.android.core.utils.rx

import io.reactivex.rxjava3.subjects.PublishSubject
import odoo.miem.android.core.utils.Result

/**
 * Just helpers for creating new instance of [PublishSubject]
 *
 * @author Vorozhtsov Mikhail
 */

fun emptyResultPublishSubject() = PublishSubject.create<Result>()

fun lazyEmptyResultPublishSubject(): Lazy<PublishSubject<Result>> = lazy { emptyResultPublishSubject() }