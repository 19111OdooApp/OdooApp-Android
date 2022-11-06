package odoo.miem.android.core.di

import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import odoo.miem.android.core.di.impl.ApiProvider
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.di.impl.ApiResolver
import odoo.miem.android.core.di.api.Api
import org.junit.Test
import odoo.miem.android.core.di.impl.exception.ProviderNotFoundException
import kotlin.test.AfterTest
import kotlin.test.assertEquals
import kotlin.test.assertFalse

/**
 * Тест для проверки [ApiRegistry]
 *
 * @see ApiRegistry
 *
 * @author Ворожцов Михаил
 */
class ApiRegistryTest {

    private val mockApi = mockk<Api>(relaxed = true)
    private val mockApiProvider = ApiProvider { mockApi }

    private val emptyMap: Map<Class<out Api>, @JvmSuppressWildcards ApiProvider> by lazy {
        emptyMap()
    }
    private val map: Map<Class<out Api>, @JvmSuppressWildcards ApiProvider> by lazy {
        mapOf(
            mockApi::class.java to mockApiProvider
        )
    }


    @Test
    fun `Проверка на нахождение нужного ApiResolver`() = runBlocking {

        // Подготовка
        ApiRegistry.init(
            newApiResolver = ApiResolver(map)
        )

        // Выполнение
        val resultApi = ApiRegistry.getApi(mockApi::class.java)

        // Проверка
        assertEquals(
            expected = mockApi,
            actual = resultApi
        )
    }

    @Test
    fun `Проверка на вызов исключения при отсутствии необходимого ApiResolver`() = runBlocking {

        // Подготовка
        ApiRegistry.init(
            newApiResolver = ApiResolver(emptyMap)
        )

        // Выполнение
        val result = try {
            ApiRegistry.getApi(mockApi::class.java)
            true
        } catch (e: ProviderNotFoundException) {
            false
        }

        // Проверка
        assertFalse(result)
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}