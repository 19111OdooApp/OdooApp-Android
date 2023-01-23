package odoo.miem.android.core.di.impl.exception

/**
 * Exception for [ApiResolver]
 *
 * @see ApiResolver
 *
 * @author Vorozhtsov Mikhail
 */
class ProviderNotFoundException(override val message: String) : RuntimeException()
