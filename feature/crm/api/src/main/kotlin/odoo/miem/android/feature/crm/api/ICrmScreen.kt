package odoo.miem.android.feature.crm.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface ICrmScreen {
    @Composable
    fun CrmScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
