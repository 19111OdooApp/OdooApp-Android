package odoo.miem.android.feature.recruitment.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * [IRecruitmentDetailsScreen] is interface for providing and
 * implementation [RecruitmentDetailsScreen]
 *
 * @author Vorozhtcov Mikhail
 */
interface IRecruitmentDetailsScreen {

    @Composable
    fun RecruitmentDetailsScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    )
}
