package odoo.miem.android.core.networkApi.authorization.impl

import io.mockk.clearAllMocks
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertIs

class AuthorizationRepositoryTest {

    private lateinit var authorizationRepository: AuthorizationRepository

    @BeforeTest
    fun setup() {
        authorizationRepository = AuthorizationRepository()
    }

    @Test
    fun `test single string`(): Unit = runBlocking {
        val login = "login"
        val password = "password"

        val result = authorizationRepository.generalAuthorization(
            login = login,
            password = password
        )

        assertIs<Single<String>>(result)
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}