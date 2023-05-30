import io.mockk.clearAllMocks
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooModulesResponse
import odoo.miem.android.core.networkApi.userModules.impl.UserModulesRepository
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertIs

class UserModulesRepositoryTest {

    private lateinit var userModulesRepositor: UserModulesRepository

    @BeforeTest
    fun setup() {
        userModulesRepositor = UserModulesRepository()
    }

    @Test
    fun `test get odoo groups`(): Unit = runBlocking {

        val result = userModulesRepositor.getOdooGroups()

        assertIs<Single<OdooGroupsResponse>>(result)
    }

    @Test
    fun `test get odoo modules`(): Unit = runBlocking {

        val result = userModulesRepositor.getOdooModules()

        assertIs<Single<OdooModulesResponse>>(result)
    }


    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}