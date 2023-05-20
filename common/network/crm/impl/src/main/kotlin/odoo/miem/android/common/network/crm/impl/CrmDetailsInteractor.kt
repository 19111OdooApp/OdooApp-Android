package odoo.miem.android.common.network.crm.impl

import odoo.miem.android.common.network.crm.api.ICrmDetailsInteractor
import odoo.miem.android.common.network.crm.api.ICrmInteractor
import odoo.miem.android.common.network.crm.api.entities.details.OpportunityInfo
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.crm.api.di.ICrmRepositoryApi
import odoo.miem.android.core.networkApi.crm.api.entities.CrmApplicationDetailsResponse
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.ResultSingle
import odoo.miem.android.core.utils.state.SuccessResult
import timber.log.Timber
import java.util.Currency
import javax.inject.Inject

/**
 * [CrmDetailsInteractor] - implementation of [ICrmInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class CrmDetailsInteractor @Inject constructor() : ICrmDetailsInteractor {

    private val crmRepository by api(ICrmRepositoryApi::crmRepository)

    override fun getOpportunityInfo(opportunityId: Long): ResultSingle<OpportunityInfo> {
        Timber.d("getOpportunityInfo(): opportunityId - $opportunityId")

        return crmRepository
            .getOpportunityInfo(opportunityId)
            .map<Result<OpportunityInfo>> { response ->
                Timber.d("getApplicationInfo(): get response - $response")
                SuccessResult(response.toDTO())
            }
            .onErrorReturn {
                Timber.e("getRecruitmentJobs(): error message = ${it.message}")
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
        expectedRevenue = expectedRevenue,
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

    private fun List<Any>?.getParam(index: Int = 1) = this?.get(index)?.let { it as? String }

    fun getCurrencySymbol(currencyCode: String?): String = currencyCode?.let {
        Currency.getInstance(it).symbol
    } ?: ""
}
