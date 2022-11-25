package odoo.miem.android.core.utils

import androidx.annotation.StringRes

// TODO Description
// sealed class Result<T>(
//     val data: T? = null,
//     @StringRes val message: Int? = null
// )
sealed interface Result

class SuccessResult<T>(data: T?) : Result

class ErrorResult<T>(@StringRes message: Int? = null) : Result

class LoadingResult<T>(data: T? = null) : Result

class NothingResult<T> : Result