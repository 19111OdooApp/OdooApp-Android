package odoo.miem.android.core.di.impl.exception

/**
 * Исключение для [ApiRegistry]
 *
 * @see ApiRegistry
 *
 * @author Ворожцов Михаил
 */
class ResolverNotInstalledException(override val message: String) : RuntimeException()
