package odoo.miem.android.feature.recruitment.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [IRecruitmentJobsScreen] is interface for providing and
 * implementation [RecruitmentJobsScreen]
 *
 * @author Vorozhtcov Mikhail
 */
interface IRecruitmentJobsScreen {

    @Composable
    fun RecruitmentJobsScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
