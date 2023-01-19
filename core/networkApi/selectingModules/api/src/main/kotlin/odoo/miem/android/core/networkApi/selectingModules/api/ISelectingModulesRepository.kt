package odoo.miem.android.core.networkApi.selectingModules.api

import io.reactivex.rxjava3.core.Single

/**
 * [ISelectiongModulesRepository] - interface for wrapping data layer
 * logic, which is connected with selecting modules
 *
 * @author Egor Danilov
 */
interface ISelectingModulesRepository {

    /**
     * [getAllModules] - function for requesting all modules of Odoo
     *
     * @param login of user
     * @param password of user
     *
     * @return Observable<Int> which provides UID of user
     */
    fun getAllModules(login: String, password: String): Single<Int>
}