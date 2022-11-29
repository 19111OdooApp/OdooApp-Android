package odoo.miem.android.core.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * States of response
 *
 * @author Vorozhtsov Mikhail
 */

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
)

class SuccessResult<T>(data: T?) : Result<T>(data = data)

class ErrorResult<T>(message: String? = null) : Result<T>(message = message)

class LoadingResult<T>(data: T? = null) : Result<T>(data = data)

object NothingResult : Result<Any>()

/**
 * Aliases for rx
 */
typealias ResultSubject<T> = PublishSubject<Result<T>>

typealias ResultObservable<T> = Observable<Result<T>>
