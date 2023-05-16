package odoo.miem.android.common.network.crm.impl

import android.content.pm.ApplicationInfo
import odoo.miem.android.common.network.crm.api.ICrmDetailsInteractor
import odoo.miem.android.common.network.crm.api.ICrmInteractor
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.networkApi.crm.api.di.ICrmRepositoryApi
import odoo.miem.android.core.utils.state.ResultSingle
import javax.inject.Inject

/**
 * [CrmDetailsInteractor] - implementation of [ICrmInteractor]
 *
 * @author Vorozhtsov Mikhail
 */
class CrmDetailsInteractor @Inject constructor() : ICrmDetailsInteractor {

    private val crmRepository by api(ICrmRepositoryApi::crmRepository)

    override fun getApplicationInfo(applicationId: Long): ResultSingle<ApplicationInfo> {
        TODO("Not yet implemented")
    }
}
