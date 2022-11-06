package odoo.miem.android.core.di.impl.exception

/**
 * Исключение для [ApiResolver]
 *
 * @see ApiResolver
 *
 * @author Ворожцов Михаил
 */
class ProviderNotFoundException(override val message: String) : RuntimeException()
