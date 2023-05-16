package odoo.miem.android.feature.crm.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface ICrmKanbanScreen {
    @Composable
    fun CrmKanbanScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
