package odoo.miem.android.di.exception

/**
 * Исключение для [ApiResolver]
 *
 * @see ApiResolver
 *
 * @author Ворожцов Михаил
 */
class ProviderNotFoundException(override val message: String) : RuntimeException()