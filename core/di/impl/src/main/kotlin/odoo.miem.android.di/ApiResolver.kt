package odoo.miem.android.di

import odoo.miem.android.di.exception.ProviderNotFoundException
import javax.inject.Inject

/**
 * [ApiResolver] - класс, который инкапсулирует в себе **Map** со всеми реализациями **Api**
 *
 * @constructor содержит [apiFactories], в котором ключом является [Api] и значением [ApiProvider] с
 * реализацией
 *
 * @see ApiProvider
 *
 * @author Ворожцов Михаил
 */
class ApiResolver @Inject constructor(
    private val apiFactories: Map<Class<out Api>, @JvmSuppressWildcards ApiProvider>
) {

    /**
     * Достает [ApiProvider] из [apiFactories]
     *
     * @param cls ключ, по которому достаем [ApiProvider] из [apiFactories]
     * @return [ApiProvider] соответствующий ключу [cls]
     */
    private fun <T : Api> getProvider(cls: Class<T>): ApiProvider =
        apiFactories[cls] ?: throw ProviderNotFoundException("Not found provider for class $cls")

    /**
     * Вызывает метод [getProvider] для предоставления нужного [ApiProvider] и
     * касту к нужному дженерику
     *
     * @param cls ключ, по которому достаем [ApiProvider] из [apiFactories]
     * @return [T] нужная реализация [Api]
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Api> getApi(cls: Class<T>): T = getProvider(cls).get() as T
}
