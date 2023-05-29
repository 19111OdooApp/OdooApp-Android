import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.core.firebaseDatabase.impl.FirebaseDatabase
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.FavouriteModulesResponse
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.ModuleIconResponse
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.StatusIconResponse
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertIs

class FirebaseDatabaseTest {

    private lateinit var firebaseDatabase: FirebaseDatabase

    @BeforeTest
    fun setup() {
        val mockFirestore = mockk<FirebaseFirestore>(relaxed = true)
        val mockFirebaseStorage = mockk<FirebaseStorage>(relaxed = true)

        firebaseDatabase = FirebaseDatabase(mockFirestore, mockFirebaseStorage)
    }

    @Test
    fun `test get module icons`(): Unit = runBlocking {
        val result = firebaseDatabase.fetchModuleIcons()

        assertIs<Single<List<ModuleIconResponse>>>(result)
    }

    @Test
    fun `test get status icons`(): Unit = runBlocking {
        val result = firebaseDatabase.fetchStatusIcons()

        assertIs<Single<List<StatusIconResponse>>>(result)
    }

    @Test
    fun `test get favorite modules`(): Unit = runBlocking {
        val uid = 0
        val userName = "userName"

        val result = firebaseDatabase.fetchFavouriteModules(uid, userName)

        assertIs<Single<FavouriteModulesResponse>>(result)
    }

    @Test
    fun `test add update user`(): Unit = runBlocking {
        val uid = 0
        val userName = "userName"
        val favouriteModules = listOf("CRM", "Recruitment")

        val result = firebaseDatabase.addOrUpdateUser(uid, userName, favouriteModules)

        assertIs<Single<Boolean>>(result)
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}