package odoo.miem.android.common.network.crm.api

import android.content.pm.ApplicationInfo
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [ICrmDetailsInteractor] - interface for handling logic
 * of recruitment application details
 *
 * @author Vorozhtos Mikhail
 */
interface ICrmDetailsInteractor {

    // TODO Remake
    /**
     * [getApplicationInfo] get base info about appication by [applicationId]
     *
     * @return [ApplicationInfo]
     */
    fun getApplicationInfo(applicationId: Long): ResultSingle<ApplicationInfo>
}
