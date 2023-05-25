package odoo.miem.android.common.network.recruitment.api

import odoo.miem.android.common.network.recruitment.api.entities.details.ApplicationInfo
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [IRecruitmentDetailsInteractor] - interface for handling logic
 * of recruitment application details
 *
 * @author Vorozhtos Mikhail
 */
interface IRecruitmentDetailsInteractor {

    /**
     * [getApplicationInfo] get base info about appication by [applicationId]
     *
     * @return [ApplicationInfo]
     */
    fun getApplicationInfo(applicationId: Long): ResultSingle<ApplicationInfo>

    fun createLogNote(userId: Long, text: String): ResultSingle<Unit>
}
