package odoo.miem.android.core.networkApi.remoteConfig.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkApi.remoteConfig.api.IRemoteConfig

/**
 * [IRemoteConfigApi] needed for wrapping over [IRemoteConfig] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IRemoteConfigApi : Api {
    val remoteConfig: IRemoteConfig
}
