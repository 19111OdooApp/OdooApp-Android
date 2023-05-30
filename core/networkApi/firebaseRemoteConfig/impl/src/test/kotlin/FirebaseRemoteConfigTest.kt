import io.mockk.clearAllMocks
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.core.networkApi.firebaseRemoteConfig.impl.FirebaseRemoteConfig
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertIs
import com.google.firebase.remoteconfig.FirebaseRemoteConfig as RealFirebaseRemoteConfig

class FirebaseRemoteConfigTest {

    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    @BeforeTest
    fun setup() {
        val mockRemoteConfig = mockk<RealFirebaseRemoteConfig>(relaxed = true)

        firebaseRemoteConfig = FirebaseRemoteConfig(mockRemoteConfig)
    }

    @Test
    fun `test get implemented modules from remote config`(): Unit = runBlocking {
        val result = firebaseRemoteConfig.fetchImplementedModules()

        assertIs<Single<String>>(result)
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}