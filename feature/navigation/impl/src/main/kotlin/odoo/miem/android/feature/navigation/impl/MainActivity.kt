package odoo.miem.android.feature.navigation.impl

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
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

        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            // TODO Picker of theme
            OdooMiemAndroidTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier.navigationBarsPadding()
                        ) {
                            Snackbar(
                                snackbarData = it,
                                containerColor = MaterialTheme.colorScheme.inverseSurface,
                                contentColor = contentColorFor(
                                    backgroundColor = MaterialTheme.colorScheme.inverseSurface
                                ),
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
}
