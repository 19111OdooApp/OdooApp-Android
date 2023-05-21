package odoo.miem.android.feature.crm.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface ICrmDetailsScreen {

    @Composable
    fun CrmDetailsScreen(
        opportunityId: Long,
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
