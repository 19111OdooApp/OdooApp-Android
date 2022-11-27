package odoo.miem.android.core.utils

/**
 * States of response
 *
 * @author Vorozhtsov Mikhail
 */

sealed interface Result

class SuccessResult<T>(data: T?) : Result

class ErrorResult(message: String? = null) : Result

class LoadingResult<T>(data: T? = null) : Result

object NothingResult : Result