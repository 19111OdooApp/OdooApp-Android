import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.common.network.selectingModules.impl.SelectingModulesInteractor
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.networkApi.firebaseDatabase.api.di.IFirebaseDatabaseApi
import odoo.miem.android.core.networkApi.firebaseRemoteConfig.api.di.IFirebaseRemoteConfigApi
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserInfoRepositoryApi
import odoo.miem.android.core.networkApi.userInfo.api.di.IUserModulesRepositoryApi
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.SuccessResult
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFails
import kotlin.test.assertIs

class SelectingModulesInteractorTest {

    private lateinit var selectingModulesInteractor: SelectingModulesInteractor

    private lateinit var mockUserInfoRepositoryApi: IUserInfoRepositoryApi
    private lateinit var mockUserModulesRepositoryApi: IUserModulesRepositoryApi
    private lateinit var mockDataStoreApi: IDataStoreApi
    private lateinit var mockFirebaseRemoteConfigApi: IFirebaseRemoteConfigApi
    private lateinit var mockFirebaseDatabaseApi: IFirebaseDatabaseApi

    @BeforeTest
    fun setup() {
        mockUserInfoRepositoryApi = mockk(relaxed = true)
        mockUserModulesRepositoryApi = mockk(relaxed = true)
        mockDataStoreApi = mockk(relaxed = true)
        mockFirebaseRemoteConfigApi = mockk(relaxed = true)
        mockFirebaseDatabaseApi = mockk(relaxed = true)

        mockkObject(ApiRegistry)
        every { ApiRegistry.getApi(IUserInfoRepositoryApi::class.java) } returns mockUserInfoRepositoryApi
        every { ApiRegistry.getApi(IUserModulesRepositoryApi::class.java) } returns mockUserModulesRepositoryApi
        every { ApiRegistry.getApi(IDataStoreApi::class.java) } returns mockDataStoreApi
        every { ApiRegistry.getApi(IFirebaseRemoteConfigApi::class.java) } returns mockFirebaseRemoteConfigApi
        every { ApiRegistry.getApi(IFirebaseDatabaseApi::class.java) } returns mockFirebaseDatabaseApi

        selectingModulesInteractor = SelectingModulesInteractor()
    }

    @Test
    fun `test get user info`(): Unit = runBlocking {
        val result = selectingModulesInteractor.getUserInfo()

        assertIs<Single<SuccessResult<User>>>(result)
    }

    @Test
    fun `test get user info failed`(): Unit = runBlocking {
        coEvery {
            mockUserInfoRepositoryApi.userInfoRepository.getUserInfo()
        } throws RuntimeException()

        assertFails {
            selectingModulesInteractor.getUserInfo()
        }
    }

    @Test
    fun `test get user info catch datastore failed`(): Unit = runBlocking {

        coEvery {
            mockDataStoreApi.dataStore.setUID(any())
        } throws RuntimeException()

        val result = selectingModulesInteractor.getUserInfo()

        assertIs<Single<ErrorResult<User>>>(result)
    }

    @Test
    fun `test get user info catch firebase failed`(): Unit = runBlocking {

        coEvery {
            mockFirebaseDatabaseApi.firebaseDatabase.fetchFavouriteModules(any(), any())
        } throws RuntimeException()

        val result = selectingModulesInteractor.getUserInfo()

        assertIs<Single<ErrorResult<User>>>(result)
    }

    @Test
    fun `test get odoo modules`(): Unit = runBlocking {
        val userUid = 1

        val result = selectingModulesInteractor.getOdooModules(userUid)

        assertIs<Single<SuccessResult<List<OdooModule>>>>(result)
    }

    @Test
    fun `test get odoo modules failed`(): Unit = runBlocking {
        val userUid = 1

        coEvery {
            mockUserModulesRepositoryApi.selectingModulesRepository.getOdooModules()
        } throws RuntimeException()

        assertFails {
            selectingModulesInteractor.getOdooModules(userUid)
        }
    }

    @Test
    fun `test update favourite modules`(): Unit = runBlocking {
        val user = mockk<User>(relaxed = true)
        val favouriteModules = listOf("CRM, Recruitment")

        val result = selectingModulesInteractor.updateFavouriteModules(
            user = user,
            favouriteModules = favouriteModules
        )

        assertIs<Single<SuccessResult<Boolean>>>(result)
    }

    @Test
    fun `test update favourite modules failed`(): Unit = runBlocking {
        val user = mockk<User>(relaxed = true)
        val favouriteModules = listOf("CRM, Recruitment")

        coEvery {
            mockFirebaseDatabaseApi.firebaseDatabase.addOrUpdateUser(any(), any(), any())
        } throws RuntimeException()

        assertFails {
            selectingModulesInteractor.updateFavouriteModules(
                user = user,
                favouriteModules = favouriteModules
            )
        }
    }


    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}