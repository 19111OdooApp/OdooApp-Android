package odoo.miem.android.di

import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import odoo.miem.android.di.exception.ProviderNotFoundException
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

/**
 * Тест для проверки [ApiResolver]
 *
 * @see ApiResolver
 *
 * @author Ворожцов Михаил
 */
class ApiResolverTest {

    @Test
    fun `Проверка нахождения нужного ApiProvider`() = runBlocking {

        // Подготовка
        val mockApi = mockk<Api>(relaxed = true)
        val mockApiProvider = ApiProvider { mockApi }

        val map: Map<Class<out Api>, @JvmSuppressWildcards ApiProvider> = mapOf(
            mockApi::class.java to mockApiProvider
        )
        val apiResolver = ApiResolver(map)

        // Выполнение
        val result = apiResolver.getApi(mockApi::class.java)

        // Проверка
        assertEquals(
            expected = result,
            actual = mockApiProvider.get()
        )
    }

    @Test
    fun `Проверка выброса исключения при отсутствии нужного ApiProvider`() = runBlocking {

        // Подготовка
        val mockApi = mockk<Api>(relaxed = true)

        val emptyMap: Map<Class<out Api>, @JvmSuppressWildcards ApiProvider> = emptyMap()
        val apiResolver = ApiResolver(emptyMap)

        // Выполнение
        val result = try {
            apiResolver.getApi(mockApi::class.java)
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