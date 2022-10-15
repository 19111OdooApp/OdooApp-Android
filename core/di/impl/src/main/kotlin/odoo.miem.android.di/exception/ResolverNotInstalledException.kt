package odoo.miem.android.di.exception

/**
 * Исключение для [ApiRegistry]
 *
 * @see ApiRegistry
 *
 * @author Ворожцов Михаил
 */
class ResolverNotInstalledException(override val message: String) : RuntimeException()