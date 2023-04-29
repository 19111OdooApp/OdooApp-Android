package odoo.miem.android.core.networkApi.userInfo.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.OdooModulesResponse

/**
 * [IUserModulesRepository] - interface for wrapping data layer
 * logic, which is connected with selecting modules
 *
 * @author Egor Danilov
 */
interface IUserModulesRepository {

    /**
     * [getOdooGroups] - function for requesting all user groups of Odoo

     * @return Single<[OdooGroupsResponse]> which provides modules accessible for user
     */
    fun getOdooGroups(): Single<OdooGroupsResponse>

    /**
     * [getOdooModules] - function for requesting all modules of Odoo

     * @return Single<[OdooModulesResponse]> which provides modules accessible for user
     */
    fun getOdooModules(): Single<OdooModulesResponse>
}
