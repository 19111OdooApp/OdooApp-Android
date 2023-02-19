package odoo.miem.android.core.networkApi.remoteConfig.api

/**
 * [IRemoteConfig] - interface for wrapping data layer
 * logic, which is connected with Firebase Remote Config
 *
 * @author Egor Danilov
 */
interface IRemoteConfig {

    /**
     * [fetchImplementedModules] - function for getting JSON with implemented module
     * from Firebase RemoteConfig
     *
     * @return String (JSON)
     */
    fun fetchImplementedModules(): String
}
