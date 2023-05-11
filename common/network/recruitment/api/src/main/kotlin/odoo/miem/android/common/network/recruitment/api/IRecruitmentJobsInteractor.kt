package odoo.miem.android.common.network.recruitment.api

import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJob
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [IRecruitmentJobsInteractor] - interface for handle logic of recruitment jobs
 *
 * @author Vorozhtos Mikhail
 */
interface IRecruitmentJobsInteractor {

    /**
     * [getRecruitmentJobs] gives all list of job
     */
    fun getRecruitmentJobs(): ResultSingle<List<RecruitmentJob>>

    /**
     * [setJobPublication] is set job with [jobId] as published or not, depends on [publish] flag
     */
    fun setJobPublication(jobId: Long, publish: Boolean): ResultSingle<Boolean>

    /**
     * [setJobFavoritable] is set job with [jobId] as favorite or not, depends on [isFavorite] flag
     */
    fun setJobFavoritable(jobId: Long, isFavorite: Boolean): ResultSingle<Boolean>

    /**
     * [setJobRecruit] is set job with [jobId] as done or not, depends on [isRecruitingDone] flag
     */
    fun setJobRecruit(jobId: Long, isRecruitingDone: Boolean): ResultSingle<Boolean>
}
