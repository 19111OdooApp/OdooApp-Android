package odoo.miem.android.core.networkApi.firebaseRemoteConfig.api.di

import odoo.miem.android.core.di.api.Api
import odoo.miem.android.core.networkApi.firebaseRemoteConfig.api.IFirebaseRemoteConfig

/**
 * [IFirebaseRemoteConfigApi] needed for wrapping over [IFirebaseRemoteConfig] and
 * providing in common **DI graph**
 *
 * @see Api
 *
 * @author Egor Danilov
 */
interface IFirebaseRemoteConfigApi : Api {
    val remoteConfig: IFirebaseRemoteConfig
}
