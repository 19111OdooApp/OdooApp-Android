package odoo.miem.android.core.di.impl

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.di.impl.exception.ResolverNotInstalledException

/**
 * [ApiProvider] - Singleton, which wraps **[ApiResolver]**
 *
 * @param apiResolver is needed for storing instance of [ApiResolver] after initialization in *app* module
 *
 * @see ApiKey
 * @see ApiResolver
 *
 * @author Vorozhtsov Mikhail
 */
object ApiRegistry {

    private var apiResolver: ApiResolver? = null

    /**
     * Method for providing [Api] from **DI Map** to [ApiResolver].
     *
     * @throws ResolverNotInstalledException in case it is not possible to find the required **Api** by key
     * @return реализация **Api**
     */
    fun <T : Api> getApi(cls: Class<T>): T =
        apiResolver?.getApi(cls) ?: throw ResolverNotInstalledException("Resolver is not installed")

    /**
     * Initialization of [apiResolver] property
     *
     * @param newApiResolver - new [ApiResolver] for storing in
     * [ApiRegistry] after initialization in *app* module
     */
    fun init(newApiResolver: ApiResolver) {
        apiResolver = newApiResolver
    }
}
