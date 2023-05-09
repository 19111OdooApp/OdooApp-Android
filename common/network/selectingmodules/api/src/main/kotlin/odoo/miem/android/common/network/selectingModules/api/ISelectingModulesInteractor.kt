package odoo.miem.android.common.network.selectingModules.api

import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.core.utils.state.ResultSingle

/**
 * [ISelectingModulesInteractor] - interface for wrapping selecting modules
 * use cases
 *
 * @author Egor Danilov
 */
interface ISelectingModulesInteractor {

    /**
     * [getUserInfo] - function for getting general user info and favourite modules of users
     *
     * @return [ResultSingle] with [User] data
     */
    fun getUserInfo(): ResultSingle<User>

    /**
     * [getOdooModules] - function for getting all necessary information about Odoo modules
     * (e.g. all modules, icons and etc)
     *
     * @return [ResultSingle] with list of [OdooModule] data
     */
    fun getOdooModules(userUid: Int): ResultSingle<List<OdooModule>>

    /**
     * [updateFavouriteModules] - function for updating favourite modules of user
     *
     * @return [ResultSingle] with [Boolean]: true if update successful, false otherwise
     */
    fun updateFavouriteModules(user: User, favouriteModules: List<String>): ResultSingle<Boolean>
}
