package odoo.miem.android.feature.recruitment.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface IRecruitmentKanbanScreen {
    @Composable
    fun RecruitmentKanbanScreen(
        navController: NavHostController,
        jobId: Long,
        showMessage: (Int) -> Unit
    )
}
