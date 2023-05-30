import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJob
import odoo.miem.android.common.network.recruitment.impl.RecruitmentJobsInteractor
import odoo.miem.android.core.dataStore.api.di.IDataStoreApi
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.networkApi.recruitment.api.di.IRecruitmentRepositoryApi
import odoo.miem.android.core.utils.state.SuccessResult
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFails
import kotlin.test.assertIs

class RecruitmentJobsInteractorTest {

    private lateinit var recruitmentJobsInteractor: RecruitmentJobsInteractor

    private lateinit var mockRecruitmentRepositoryApi: IRecruitmentRepositoryApi
    private lateinit var mockDataStoreApi: IDataStoreApi

    @BeforeTest
    fun setup() {
        mockRecruitmentRepositoryApi = mockk(relaxed = true)
        mockDataStoreApi = mockk(relaxed = true)

        mockkObject(ApiRegistry)
        every { ApiRegistry.getApi(IRecruitmentRepositoryApi::class.java) } returns mockRecruitmentRepositoryApi
        every { ApiRegistry.getApi(IDataStoreApi::class.java) } returns mockDataStoreApi

        recruitmentJobsInteractor = RecruitmentJobsInteractor()
    }

    @Test
    fun `test get recruitment jobs`(): Unit = runBlocking {
        val result = recruitmentJobsInteractor.getRecruitmentJobs()

        assertIs<Single<SuccessResult<List<RecruitmentJob>>>>(result)
    }

    @Test
    fun `test get recruitment jobs failed`(): Unit = runBlocking {
        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.getRecruitmentJobsInfo()
        } throws RuntimeException()

        assertFails {
            recruitmentJobsInteractor.getRecruitmentJobs()
        }
    }

    @Test
    fun `test set job publication`(): Unit = runBlocking {
        val jobId = 1L
        val publish = true

        val result = recruitmentJobsInteractor.setJobPublication(
            jobId = jobId,
            publish = publish,
        )

        assertIs<Single<SuccessResult<Boolean>>>(result)
    }

    @Test
    fun `test set job publication failed`(): Unit = runBlocking {
        val jobId = 1L
        val publish = true

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.setJobPublication(
                jobId = jobId,
                publish = publish,
            )
        } throws RuntimeException()

        assertFails {
            recruitmentJobsInteractor.setJobPublication(
                jobId = jobId,
                publish = publish,
            )
        }
    }

    @Test
    fun `test set job favoritable`(): Unit = runBlocking {
        val jobId = 1L
        val isFavorite = true

        val result = recruitmentJobsInteractor.setJobFavoritable(
            jobId = jobId,
            isFavorite = isFavorite,
        )

        assertIs<Single<SuccessResult<Boolean>>>(result)
    }

    @Test
    fun `test set job favoritable failed`(): Unit = runBlocking {
        val jobId = 1L
        val isFavorite = true

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.setJobFavoritable(
                jobId = jobId,
                isFavorite = isFavorite,
            )
        } throws RuntimeException()

        assertFails {
            recruitmentJobsInteractor.setJobFavoritable(
                jobId = jobId,
                isFavorite = isFavorite,
            )
        }
    }

    @Test
    fun `test set job recruit`(): Unit = runBlocking {
        val jobId = 1L
        val isRecruitingDone = true

        val result = recruitmentJobsInteractor.setJobRecruit(
            jobId = jobId,
            isRecruitingDone = isRecruitingDone,
        )

        assertIs<Single<SuccessResult<Boolean>>>(result)
    }

    @Test
    fun `test set job recruit failed`(): Unit = runBlocking {
        val jobId = 1L
        val isRecruitingDone = true

        coEvery {
            mockRecruitmentRepositoryApi.recruitmentRepository.setJobRecruit(
                jobId = jobId,
                isRecruitingDone = isRecruitingDone,
            )
        } throws RuntimeException()

        assertFails {
            recruitmentJobsInteractor.setJobRecruit(
                jobId = jobId,
                isRecruitingDone = isRecruitingDone,
            )
        }
    }

    @AfterTest
    fun clear() {
        clearAllMocks()
    }
}