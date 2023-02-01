package odoo.miem.android.core.networkApi.selectingModules.api

import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.selectingModules.api.source.OdooGroup
import odoo.miem.android.core.networkApi.selectingModules.api.source.UserInfoResponse

/**
 * [ISelectiongModulesRepository] - interface for wrapping data layer
 * logic, which is connected with selecting modules
 *
 * @author Egor Danilov
 */
interface ISelectingModulesRepository {

    fun getUserInfo(): Single<UserInfoResponse>

    /**
     * [getAllModules] - function for requesting all modules of Odoo

     * @return Observable<[OdooGroup]> which provides modules accessible for user
     */
    fun getAllModules(): Single<List<OdooGroup>>
}