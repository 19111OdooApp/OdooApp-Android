package odoo.miem.android.di

import odoo.miem.android.di.exception.ResolverNotInstalledException

/**
 * [ApiProvider] - Singleton класс, который нужен для обертки **ApiResolver**
 *
 * @param apiResolver нужен для хранения инстанса [ApiResolver] после инициализации в *app* модуле
 *
 * @see ApiKey
 * @see ApiResolver
 *
 * @author Ворожцов Михаил
 */
object ApiRegistry {

    private var apiResolver: ApiResolver? = null

    /**
     * Метод для предоставления [Api] из **Map** в [ApiResolver].
     *
     * @throws ResolverNotInstalledException в случае, если не удается найти нужный **Api** по ключу
     * @return реализация **Api**
     */
    fun <T : Api> getApi(cls: Class<T>): T =
        apiResolver?.getApi(cls) ?: throw ResolverNotInstalledException("Resolver is not installed")

    /**
     * Инициализация [apiResolver] свойства
     *
     * @param newApiResolver новый [ApiResolver] для сохранения в [ApiRegistry] после начальной инициализации в *app* модуле
     */
    fun init(newApiResolver: ApiResolver) {
        apiResolver = newApiResolver
    }
}
