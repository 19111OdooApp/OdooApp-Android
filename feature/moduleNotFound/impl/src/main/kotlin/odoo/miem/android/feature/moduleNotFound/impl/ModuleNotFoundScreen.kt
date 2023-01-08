package odoo.miem.android.feature.moduleNotFound.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.text.DisplayText
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.feature.moduleNotFound.api.IModuleNotFoundScreen
import javax.inject.Inject

/**
 * [ModuleNotFoundScreen] - support screen for [SelectingModulesScreen]
 * appears if selected module is not implemented yet
 *
 * @author Egor Danilov
 */
class ModuleNotFoundScreen @Inject constructor() : IModuleNotFoundScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun ModuleNotFoundScreen(
        navController: NavHostController
    ) {
        ModuleNotFoundScreenContent(
            onBackButtonClick = navController::popBackStack
        )
    }

    @Composable
    private fun ModuleNotFoundScreenContent(
        onBackButtonClick: () -> Unit
    ) = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        SimpleLogoAppBar(onBackButtonClick = onBackButtonClick)

        Spacer(modifier = Modifier.height(170.dp))

        DisplayText(
            textRes = R.string.module_not_found_header,
            color = MaterialTheme.colorScheme.primary
        )

        SubtitleText(
            textRes = R.string.module_not_found_desc,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 36.dp)
        )
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
    private fun ModuleNotFoundScreenPreview() = OdooMiemAndroidTheme {
        ModuleNotFoundScreenContent {}
    }
}
