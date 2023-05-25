package odoo.miem.android.feature.recruitment.impl.screen.kanban

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.network.recruitment.api.entities.kanban.Employee
import odoo.miem.android.common.network.recruitment.api.entities.kanban.Status
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.RecruitmentLikeScreen
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.stateholder.error.ErrorScreen
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.navigation.api.utils.navigateToUserProfile
import odoo.miem.android.feature.recruitment.api.IRecruitmentKanbanScreen
import odoo.miem.android.feature.recruitment.impl.R
import odoo.miem.android.feature.recruitment.impl.RecruitmentViewModel
import javax.inject.Inject

/**
 * [RecruitmentKanbanScreen] implementation of [IRecruitmentKanbanScreen]
 *
 * @author Vorozhtsov Mikhail
 */
class RecruitmentKanbanScreen @Inject constructor() : IRecruitmentKanbanScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun RecruitmentKanbanScreen(
        navController: NavHostController,
        jobId: Long,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: RecruitmentViewModel = viewModel()

        val statusList by viewModel.statusState.collectAsState()

        val userInfo by viewModel.userInfoState.collectAsState()
        userInfo.subscribeOnError(showMessage)

        val changeStatusState by viewModel.changeStatusState.collectAsState()
        changeStatusState.subscribeOnError(showMessage)

        val onNavigateToModulesPressed = {
            navController.navigate(Routes.selectingModules)
        }

        val onUserIconClick = {
            navController.navigateToUserProfile()
        }

        LaunchedEffect(Unit) {
            viewModel.onOpenKanban(jobId)
        }

        StateHolder(
            state = statusList,
            loadingContent = { LoadingScreen() },
            errorContent = {
                ErrorScreen(
                    isSessionExpired = it.isSessionExpired,
                    onSessionExpired = { navController.navigate(Routes.authorization) },
                    onRetry = { viewModel.onOpenKanban(jobId) }
                )
            },
            successContent = { result ->
                RecruitmentLikeScreen(
                    userName = userInfo.data?.name ?: "Cool user",
                    statusList = result.data ?: emptyList(),
                    onNavigateToModulesPressed = onNavigateToModulesPressed,
                    onStatusClick = { employee, status ->
                        viewModel.changeEmployeeStatus(
                            jobId,
                            employee,
                            status
                        )
                    },
                    onNewStatusCreated = { viewModel.createNewStatus(jobId, it) },
                    onUserIconClick = onUserIconClick,
                    onEmployeeCardClick = {
                        navController.navigate("${Routes.recruitmentDetails}/${it.id}")
                    },
                    onBackPressed = {
                        navController.popBackStack()
                    },
                    searchHintRes = R.string.recruitment_search_hint
                )
            }
        )
    }

    @Suppress("MagicNumber")
    @Preview(showBackground = true)
    @Composable
    fun RecruitmentScreenContentPreview() {
        OdooMiemAndroidTheme {
            RecruitmentLikeScreen(
                userName = "Cool user",
                statusList =
                listOf(
                    Status(
                        "1",
                        listOf(
                            Employee(
                                "anna",
                                2.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            )
                        ),
                    ),
                    Status(
                        "2",
                        listOf(
                            Employee(
                                "alex",
                                2.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "alex",
                                2.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "alex",
                                2.0,
                                null,
                                0,
                                DeadlineStatus.EXPIRED
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                0,
                                DeadlineStatus.ACTIVE
                            ),
                            Employee(
                                "alex",
                                2.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            )
                        ),
                    )
                ),
                onNavigateToModulesPressed = {},
                onStatusClick = { _, _ -> },
                onNewStatusCreated = {},
                searchHintRes = R.string.recruitment_search_hint,
            )
        }
    }
}
