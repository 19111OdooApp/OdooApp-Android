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

    // TODO Descr
    fun getApplicationInfo(applicationId: Long): ResultSingle<ApplicationInfo>
}
