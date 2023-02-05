package odoo.miem.android.core.networkApi.selectingModules.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooGroupsResponse
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooModulesResponse

/**
 * [ISelectingModulesRepository] - interface for wrapping data layer
 * logic, which is connected with selecting modules
 *
 * @author Egor Danilov
 */
interface ISelectingModulesRepository {

    /**
     * [getOdooGroups] - function for requesting all user groups of Odoo

     * @return Observable<[OdooGroupsResponse]> which provides modules accessible for user
     */
    fun getOdooGroups(): Single<OdooGroupsResponse>

    /**
     * [getOdooModules] - function for requesting all modules of Odoo

     * @return Observable<[OdooModulesResponse]> which provides modules accessible for user
     */
    fun getOdooModules(): Single<OdooModulesResponse>
}