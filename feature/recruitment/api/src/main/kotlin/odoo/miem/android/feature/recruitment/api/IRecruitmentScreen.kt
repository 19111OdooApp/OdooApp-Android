package odoo.miem.android.feature.recruitment.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface IRecruitmentScreen {
    @Composable
    fun RecruitmentScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
