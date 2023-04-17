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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.feature.navigation.impl.ui.Navigation
import timber.log.Timber

/**
 * [MainActivity] - entry activity which starts navigation
 * across app
 *
 * @author Vorozhtsov Mikhail
 */
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")

        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            // TODO Picker of theme
            OdooMiemAndroidTheme {
                SetupSystemBarsColors()

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

    @Composable
    private fun SetupSystemBarsColors() {
        val view = LocalView.current
        if (!view.isInEditMode) {
            val currentWindow = (view.context as? Activity)?.window
                ?: error("Not in an activity - unable to get Window reference")

            val color = MaterialTheme.colorScheme.background.toArgb()
            val isLightStatusBar = !isSystemInDarkTheme()

            SideEffect {
                currentWindow.statusBarColor = color
                currentWindow.navigationBarColor = color

                WindowCompat.getInsetsController(currentWindow, view)
                    .isAppearanceLightStatusBars = isLightStatusBar
            }
        }
    }
}
