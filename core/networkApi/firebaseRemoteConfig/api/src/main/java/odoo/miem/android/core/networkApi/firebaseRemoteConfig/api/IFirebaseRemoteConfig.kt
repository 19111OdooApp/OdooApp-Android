package odoo.miem.android.core.networkApi.firebaseRemoteConfig.api

import io.reactivex.rxjava3.core.Single

/**
 * [IFirebaseRemoteConfig] - interface for wrapping data layer
 * logic, which is connected with Firebase Remote Config
 *
 * @author Egor Danilov
 */
interface IFirebaseRemoteConfig {

    /**
     * [fetchImplementedModules] - function for getting JSON with implemented module
     * from Firebase RemoteConfig
     *
     * @return String (JSON)
     */
    fun fetchImplementedModules(): Single<String>
}
