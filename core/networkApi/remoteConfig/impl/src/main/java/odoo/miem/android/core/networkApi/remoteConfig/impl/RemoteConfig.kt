package odoo.miem.android.core.networkApi.remoteConfig.impl

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.remoteConfig.api.IRemoteConfig
import timber.log.Timber
import javax.inject.Inject

/**
 * [RemoteConfig] - wrapper of Firebase Remote Config for providing it to DI graph then
 *
 * @author Egor Danilov
 */
class RemoteConfig @Inject constructor() : IRemoteConfig {

    private val remoteConfig: FirebaseRemoteConfig by lazy {
        Firebase.remoteConfig
    }

    init {
        initRemoteConfig()
    }

    override fun fetchImplementedModules(): Single<String> {
        return Single.create { emitter ->
            remoteConfig.fetchAndActivate()
                .addOnSuccessListener {
                    val implementedModules = remoteConfig[IMPLEMENTED_MODULES_KEY].asString()

                    Timber.d("fetchImplementedModules: result = $implementedModules")
                    emitter.onSuccess(implementedModules)
                }
                .addOnFailureListener { e ->
                    Timber.e("fetchImplementedModules: error = ${e.message}")
                    emitter.onError(e)
                }
        }
    }

    private fun initRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = FIREBASE_FETCH_INTERVAL
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(
            mapOf(IMPLEMENTED_MODULES_KEY to IMPLEMENTED_MODULES_DEFAULT)
        )
    }

    private companion object {
        const val FIREBASE_FETCH_INTERVAL = 3600L

        const val IMPLEMENTED_MODULES_KEY = "implementedModules"
        const val IMPLEMENTED_MODULES_DEFAULT = "{\"modules\": []}"
    }
}
