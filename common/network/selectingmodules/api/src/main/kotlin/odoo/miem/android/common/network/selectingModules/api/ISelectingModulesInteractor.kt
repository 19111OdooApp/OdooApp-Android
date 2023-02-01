package odoo.miem.android.common.network.selectingModules.api

import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [ISelectingModulesInteractor] - interface for wrapping selecting modules
 * use cases
 *
 * @author Egor Danilov
 */
interface ISelectingModulesInteractor {

    fun getUserInfo(): ResultSingle<Unit>
}
