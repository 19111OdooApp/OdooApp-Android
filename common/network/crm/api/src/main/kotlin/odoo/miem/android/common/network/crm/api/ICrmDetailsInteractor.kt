package odoo.miem.android.common.network.crm.api

import android.content.pm.ApplicationInfo
import odoo.miem.android.common.network.crm.api.entities.details.OpportunityInfo
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [ICrmDetailsInteractor] - interface for handling logic
 * of recruitment application details
 *
 * @author Vorozhtos Mikhail
 */
interface ICrmDetailsInteractor {

    /**
     * [getApplicationInfo] get base info about appication by [applicationId]
     *
     * @return [ApplicationInfo]
     */
    fun getOpportunityInfo(applicationId: Long): ResultSingle<OpportunityInfo>
}
