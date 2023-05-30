import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.common.network.authorization.impl.AuthorizationInteractor
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.networkApi.authorization.api.di.IAuthorizationRepositoryApi
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.ResultSingle
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFails
import kotlin.test.assertIs

class AuthorizationInteractorTest {

    private lateinit var authorizationInteractor: AuthorizationInteractor

    private lateinit var mockAuthorizationRepositoryApi: IAuthorizationRepositoryApi
    private lateinit var mockDataStoreApi: IDataStoreApi

    @BeforeTest
    fun setup() {
        mockAuthorizationRepositoryApi = mockk(relaxed = true)
        mockDataStoreApi = mockk(relaxed = true)

        mockkObject(ApiRegistry)
        every { ApiRegistry.getApi(IAuthorizationRepositoryApi::class.java) } returns mockAuthorizationRepositoryApi
        every { ApiRegistry.getApi(IDataStoreApi::class.java) } returns mockDataStoreApi

        authorizationInteractor = AuthorizationInteractor()
    }

    @Test
    fun `test general auth return correct result`(): Unit = runBlocking {
        val baseUrl = "https://crm.auditory.ru/"
        val login = "admin"
        val password = "admin"

        val result = authorizationInteractor.generalAuthorization(
            baseUrl = baseUrl,
            login = login,
            password = password
        )

        assertIs<ResultSingle<Unit>>(result)
    }


    @Test
    fun `test general auth repository failed`(): Unit = runBlocking {
        val baseUrl = "https://crm.auditory.ru/"
        val login = "wronglogin"
        val password = "wrongpassword"

        coEvery {
            mockAuthorizationRepositoryApi.authorizationRepository.generalAuthorization(
                any(),
                any()
            )
        } throws RuntimeException()

        assertFails {
            authorizationInteractor.generalAuthorization(
                baseUrl = baseUrl,
                login = login,
                password = password
            )
        }
    }

    @Test
    fun `test general auth datastore failed`(): Unit = runBlocking {
        val baseUrl = "https://crm.auditory.ru/"
        val login = "wronglogin"
        val password = "wrongpassword"

        coEvery {
            mockDataStoreApi.dataStore.setAuthorized(any())
        } throws RuntimeException()

        val result = authorizationInteractor.generalAuthorization(
            baseUrl = baseUrl,
            login = login,
            password = password
        )

        assertIs<Single<ErrorResult<Unit>>>(result)
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}