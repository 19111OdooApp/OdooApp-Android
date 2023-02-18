package odoo.miem.android.feature.navigation.impl

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.feature.navigation.impl.ui.Navigation
import timber.log.Timber

/**
 * [MainActivity] - входная activity, с которой начинается
 * навигация по приложению
 *
 * @author Ворожцов Михаил
 */
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = FIREBASE_FETCH_INTERVAL
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.i("Fetching RemoteConfig successful")
                } else {
                    Timber.e("Error happened during fetching RemoteConfig: ${task.exception}")
                }
            }

        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            // TODO Picker of theme
            OdooMiemAndroidTheme {
                val view = LocalView.current
                if (!view.isInEditMode) {
                    val currentWindow = (view.context as? Activity)?.window
                        ?: error("Not in an activity - unable to get Window reference")

                    val color = MaterialTheme.colorScheme.background.toArgb()
                    val isLightStatusBar = !isSystemInDarkTheme()

                    SideEffect {
                        currentWindow.statusBarColor = color
                        WindowCompat.getInsetsController(currentWindow, view)
                            .isAppearanceLightStatusBars = isLightStatusBar
                    }
                }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier.navigationBarsPadding()
                        ) {
                            Snackbar(
                                snackbarData = it,
                                containerColor = MaterialTheme.colorScheme.inverseSurface,
                                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                                shape = MaterialTheme.shapes.small
                            )
                        }
                    },
                    content = {
                        Navigation(
                            snackbarHostState = snackbarHostState,
                            paddingValues = it,
                            navController = navController
                        )
                    }
                )
            }
        }
    }

    private companion object {
        const val FIREBASE_FETCH_INTERVAL = 3600L
    }
}
