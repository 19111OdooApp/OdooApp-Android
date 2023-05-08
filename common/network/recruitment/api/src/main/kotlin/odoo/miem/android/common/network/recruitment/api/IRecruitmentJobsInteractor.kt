package odoo.miem.android.common.network.recruitment.api

import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJob
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [IRecruitmentJobsInteractor] - interface for handle logic of recruitment jobs
 *
 * @author Vorozhtos Mikhail
 */
interface IRecruitmentJobsInteractor {

    // TODO Desc
    fun getRecruitmentJobs(): ResultSingle<List<RecruitmentJob>>

    fun setJobPublication(jobId: Long, publish: Boolean): ResultSingle<Boolean>

    fun setJobFavoritable(jobId: Long, isFavorite: Boolean): ResultSingle<Boolean>

    fun setJobRecruit(jobId: Long, isRecruitingDone: Boolean): ResultSingle<Boolean>
}
