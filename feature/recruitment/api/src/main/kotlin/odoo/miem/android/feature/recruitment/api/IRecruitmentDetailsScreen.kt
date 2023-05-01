package odoo.miem.android.feature.recruitment.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface IRecruitmentDetailsScreen {
    @Composable
    fun RecruitmentDetailsScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
