package odoo.miem.android.feature.recruitment.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [IRecruitmentKanbanScreen] is interface for providing and
 * implementation [RecruitmentKanbanScreen]
 *
 * @author Vorozhtcov Mikhail
 */
interface IRecruitmentKanbanScreen {
    @Composable
    fun RecruitmentKanbanScreen(
        navController: NavHostController,
        jobId: Long,
        showMessage: (Int) -> Unit
    )
}
