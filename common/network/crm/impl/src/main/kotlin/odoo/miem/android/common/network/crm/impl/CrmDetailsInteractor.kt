package odoo.miem.android.common.network.crm.impl

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.common.network.crm.api.ICrmDetailsInteractor
import odoo.miem.android.common.network.crm.api.ICrmInteractor
import odoo.miem.android.common.network.crm.api.entities.details.LogNoteInfo
import odoo.miem.android.common.network.crm.api.entities.details.OpportunityInfo
import odoo.miem.android.common.network.crm.api.entities.details.ScheduleActivityInfo
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.crm.api.di.ICrmRepositoryApi
import odoo.miem.android.core.networkApi.crm.api.entities.CrmApplicationDetailsResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmLogNoteResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmScheduleActivityResponse
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

/**
 * [CrmDetailsInteractor] - implementation of [ICrmInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class CrmDetailsInteractor @Inject constructor() : ICrmDetailsInteractor {

    private val crmRepository by api(ICrmRepositoryApi::crmRepository)

    private val inputFormatter by lazy { SimpleDateFormat("yyyy-MM-dd HH:mm:ss") }
    private val outputFormatter by lazy { SimpleDateFormat("dd MMMM YYYY HH:mm:ss") }

    private val deadlineInputFormatter by lazy { SimpleDateFormat("yyyy-MM-dd") }
    private val deadlineOutputFormatter by lazy { SimpleDateFormat("dd MMMM") }

    override fun getOpportunityInfo(opportunityId: Long): ResultSingle<OpportunityInfo> {
        Timber.d("getOpportunityInfo(): opportunityId - $opportunityId")

        return crmRepository
            .getOpportunityInfo(opportunityId)
            .map { response ->
                Timber.d("getOpportunityInfo(): get response - $response")
                response.toDTO()
            }
            .flatMap { opportunityInfo ->
                crmRepository.getLogNotes(opportunityInfo.id)
                    .map { logNotes ->
                        Timber.d("List of notes: $logNotes")
                        opportunityInfo.copy(logNotes = logNotes.map { it.toDTO() })
                    }
            }
            .flatMap { applicationInfo ->
                val activityIds = applicationInfo.activityIds
                if (activityIds?.isNotEmpty() == true) {
                    crmRepository.getScheduleActivities(activityIds)
                        .map { scheduleActivities ->
                            Timber.d("List of activities: $scheduleActivities")
                            applicationInfo.copy(scheduleActivities = scheduleActivities.map { it.toDTO() })
                        }
                } else {
                    Single.just(applicationInfo)
                }
            }
            .map<Result<OpportunityInfo>> {
                SuccessResult(it)
            }
            .onErrorReturn {
                Timber.e("getRecruitmentJobs(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    override fun createLogNote(opportunityId: Long, text: String): ResultSingle<Unit> {
        Timber.d("createLogNote(): opportunityId - $opportunityId, text - $text")

        return crmRepository
            .createLogNote(opportunityId, text)
            .map<Result<Unit>> { SuccessResult() }
            .onErrorReturn {
                Timber.e("createLogNote(): error message = ${it.message}")
                ErrorResult(
                    isSessionExpired = ErrorResult.isSessionExpiredMessage(it.message)
                )
            }
    }

    private fun CrmApplicationDetailsResponse.toDTO() = OpportunityInfo(
        id = checkNotNull(id),
        stageName = stageInfo.getParam(),
        partnerName = partnerName,
        opportunityName = opportunityName,
        fullName = fullName,
        sellerPhone = sellerPhone,
        sellerMobile = sellerMobile,
        sellerEmail = sellerEmail,
        companyCurrencySymbol = getCurrencySymbol(companyCurrency.getParam()),
        expectedRevenue = expectedRevenue ?: 0.0,
        probability = probability ?: 0.0,
        sellerName = sellerInfo.getParam(),
        sellerTeamUserName = sellerTeamUserInfo.getParam(),
        sellerCompanyUserName = sellerCompanyUserInfo.getParam(),
        opportunityStreet = opportunityStreet,
        opportunityCity = opportunityCity,
        opportunityStateName = opportunityStateInfo.getParam(),
        opportunityZip = opportunityZip,
        opportunityCountryName = opportunityCountryInfo.getParam(),
        opportunityWebsite = opportunityWebsite,
        opportunityDeadline = opportunityDeadline,
        opportunityRating = opportunityRating?.toDoubleOrNull() ?: 0.0,
        opportunitySummary = opportunitySummary,
        campaignName = campaignInfo.getParam(),
        mediumName = mediumInfo.getParam(),
        sourceName = sourceInfo.getParam(),
        dayToAssign = dayToAssign,
        dayToClose = dayToClose,
        activityIds = activityIds,
        messageIds = messageIds
    )

    private fun CrmLogNoteResponse.toDTO() = LogNoteInfo(
        id = checkNotNull(id),
        date = date?.let { date ->
            val inputDate = inputFormatter.parse(date)
            inputDate?.let {
                outputFormatter.format(it)
            } ?: ""
        } ?: "",
        message = message,
        authorId = authorInfo.getParam(0)?.toLongOrNull(),
        authorName = authorInfo.getParam(),
        postType = postInfo.getParam(),
        trackingInfoList = trackingInfoList ?: emptyList(),
        subtypeDescription = subtypeDescription,
    )

    private fun CrmScheduleActivityResponse.toDTO() = ScheduleActivityInfo(
        id = checkNotNull(id),
        activityName = activityInfo.getParam(),
        note = note,
        createUserId = createUserInfo.getParam(0)?.toLongOrNull(),
        createUserName = createUserInfo.getParam(),
        assignUserId = assignUserInfo.getParam(0)?.toLongOrNull(),
        assignUserName = assignUserInfo.getParam(),
        createDate = createDate?.let { date ->
            val inputDate = inputFormatter.parse(date)
            inputDate?.let {
                outputFormatter.format(it)
            } ?: ""
        } ?: "",
        deadline = deadline?.let { date ->
            val inputDate = deadlineInputFormatter.parse(date)
            inputDate?.let {
                deadlineOutputFormatter.format(it)
            } ?: ""
        } ?: "",
        state = state?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        } ?: "Not set"
    )

    private fun List<Any>?.getParam(index: Int = 1) = this?.get(index)?.let { it as? String }

    fun getCurrencySymbol(currencyCode: String?): String = currencyCode?.let {
        Currency.getInstance(it).symbol
    } ?: ""
}
