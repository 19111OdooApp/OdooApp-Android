package odoo.miem.android.feature.selectingModules.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen
import javax.inject.Inject

/**
 * [SelectingModulesScreen] implementation of [ISelectingModulesScreen]
 *
 * Methods by purpose:
 * - [SelectingModulesScreen] - the input point to this screen is needed for initial initialization.
 * For example, ViewModel initialization
 * - [SelectingModulesScreenContent] - directly layout of this screen
 * - [SelectingModulesScreenPreview] - preview of the layout that turned out in [SelectingModulesScreenContent]
 *
 * @author Vorozhtsov Mikhail
 */
class SelectingModulesScreen @Inject constructor() : ISelectingModulesScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun SelectingModulesScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {

        // TODO Create base with loading handling
        SelectingModulesScreenContent(

        )
    }

    @Composable
    private fun SelectingModulesScreenContent(

    ) = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().imePadding()
    ) {

        SimpleLogoAppBar()

    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
    private fun SelectingModulesScreenPreview() = OdooMiemAndroidTheme {
        SelectingModulesScreenContent()
    }
}
