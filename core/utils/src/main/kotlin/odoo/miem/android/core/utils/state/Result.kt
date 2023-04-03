package odoo.miem.android.core.utils.state

import androidx.annotation.StringRes
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * States of response
 *
 * @author Vorozhtsov Mikhail
 */

sealed class Result<T>(
    val data: T? = null,
    @StringRes val message: Int? = null
)

class SuccessResult<T>(data: T? = null) : Result<T>(data = data)

class ErrorResult<T>(@StringRes message: Int? = null) : Result<T>(message = message)

class LoadingResult<T>(data: T? = null) : Result<T>(data = data)

class NothingResult<T> : Result<T>()

/**
 * Aliases for rx
 */
typealias ResultSubject<T> = PublishSubject<Result<T>>

typealias StateResultSubject<T> = BehaviorSubject<Result<T>>

typealias ResultSingle<T> = Single<Result<T>>
