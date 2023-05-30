import io.mockk.clearAllMocks
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.core.networkApi.userInfo.api.source.UserInfoResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.UserProfileResponse
import odoo.miem.android.core.networkApi.userInfo.impl.UserInfoRepository
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertIs

class UserInfoRepositoryTest {

    private lateinit var userInfoRepository: UserInfoRepository

    @BeforeTest
    fun setup() {
        userInfoRepository = UserInfoRepository()
    }

    @Test
    fun `test get user info`(): Unit = runBlocking {

        val result = userInfoRepository.getUserInfo()

        assertIs<Single<UserInfoResponse>>(result)
    }

    @Test
    fun `test get user profile`(): Unit = runBlocking {
        val userId = 1L

        val result = userInfoRepository.getUserProfile(
            userId = userId
        )

        assertIs<Single<UserProfileResponse>>(result)
    }


    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}