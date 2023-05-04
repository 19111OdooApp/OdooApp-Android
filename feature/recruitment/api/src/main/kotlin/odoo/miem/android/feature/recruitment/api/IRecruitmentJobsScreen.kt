package odoo.miem.android.feature.recruitment.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface IRecruitmentJobsScreen {

    @Composable
    fun RecruitmentJobsScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
