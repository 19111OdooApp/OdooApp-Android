package odoo.miem.android.feature.crm.impl.screen.kanban

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.network.crm.api.entities.kanban.OpportunityCRM
import odoo.miem.android.common.network.crm.api.entities.kanban.StatusCRM
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.RecruitmentLikeScreen
import odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.common.uiKitComponents.stateholder.StateHolder
import odoo.miem.android.common.uiKitComponents.stateholder.error.ErrorScreen
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.crm.api.ICrmKanbanScreen
import odoo.miem.android.feature.crm.impl.CrmViewModel
import odoo.miem.android.feature.crm.impl.R
import odoo.miem.android.feature.navigation.api.data.Routes
import odoo.miem.android.feature.navigation.api.utils.navigateToUserProfile
import javax.inject.Inject

class CrmKanbanScreen @Inject constructor() : ICrmKanbanScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun CrmKanbanScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: CrmViewModel = viewModel()

        val statusList by viewModel.statusState.collectAsState()

        val userInfo by viewModel.userInfoState.collectAsState()
        userInfo.subscribeOnError(showMessage)

        val onNavigateToModulesPressed = {
            navController.navigate(Routes.selectingModules)
        }

        val onUserIconClick = {
            navController.navigateToUserProfile()
        }

        LaunchedEffect(Unit) {
            viewModel.onOpenKanban()
        }

        StateHolder(
            state = statusList,
            loadingContent = { LoadingScreen() },
            errorContent = {
                ErrorScreen(
                    isSessionExpired = it.isSessionExpired,
                    onSessionExpired = { navController.navigate(Routes.authorization) },
                    onRetry = { viewModel.onOpenKanban() }
                )
            },
            successContent = { result ->
                RecruitmentLikeScreen(
                    userName = userInfo.data?.name ?: "Cool user",
                    statusList = result.data ?: emptyList(),
                    onNavigateToModulesPressed = onNavigateToModulesPressed,
                    onStatusClick = { opportunity, status ->
                        viewModel.changeEmployeeStatus(
                            opportunity = opportunity,
                            status = status
                        )
                    },
                    onNewStatusCreated = {
                        viewModel.createNewStatus(it)
                    },
                    onUserIconClick = onUserIconClick,
                    onEmployeeCardClick = {
                        navController.navigate("${Routes.crmDetails}/${it.id}")
                    },
                    onBackPressed = {
                        navController.popBackStack()
                    },
                    searchHintRes = R.string.crm_search_hint
                )
            }
        )
    }

    @Suppress("MagicNumber")
    @Preview(showBackground = true)
    @Composable
    fun CrmScreenContentPreview() {
        OdooMiemAndroidTheme {
            RecruitmentLikeScreen(
                userName = "Cool User",
                statusList =
                listOf(
                    StatusCRM(
                        "1",
                        listOf(
                            OpportunityCRM(
                                "anna",
                                2.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            )
                        )
                    ),
                    StatusCRM(
                        "2",
                        listOf(
                            OpportunityCRM(
                                "alex",
                                2.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            ),
                            OpportunityCRM(
                                "misha",
                                3.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            ),
                            OpportunityCRM(
                                "alex",
                                2.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            ),
                            OpportunityCRM(
                                "misha",
                                3.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            ),
                            OpportunityCRM(
                                "alex",
                                2.0,
                                null,
                                0,
                                DeadlineStatus.EXPIRED
                            ),
                            OpportunityCRM(
                                "misha",
                                3.0,
                                null,
                                0,
                                DeadlineStatus.ACTIVE
                            ),
                            OpportunityCRM(
                                "alex",
                                2.0,
                                null,
                                0,
                                DeadlineStatus.NO_TASKS
                            ),
                            OpportunityCRM(
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
                searchHintRes = R.string.crm_search_hint,
            )
        }
    }
}
