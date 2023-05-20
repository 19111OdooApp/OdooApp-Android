package odoo.miem.android.feature.crm.impl.crmScreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import odoo.miem.android.common.uiKitComponents.progressbar.LoadingScreen
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.RecruitmentLikeScreen
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.utils.rx.collectAsState
import odoo.miem.android.core.utils.state.Result
import odoo.miem.android.core.utils.state.SuccessResult
import odoo.miem.android.core.utils.state.subscribeOnError
import odoo.miem.android.feature.crm.api.ICrmScreen
import odoo.miem.android.feature.crm.impl.CrmViewModel
import odoo.miem.android.feature.crm.impl.R
import odoo.miem.android.feature.crm.impl.data.Employee
import odoo.miem.android.feature.crm.impl.data.Status
import odoo.miem.android.feature.navigation.api.data.Routes
import javax.inject.Inject

class CrmScreen @Inject constructor() : ICrmScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun CrmScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        val viewModel: CrmViewModel = viewModel()

        val statusList by viewModel.statusState.collectAsState()

        statusList.subscribeOnError(showMessage)

        val onNavigateToModulesPressed = {
            navController.navigate(Routes.selectingModules)
        }

        val onUserIconClick = {
            navController.navigate(Routes.userProfile)
        }

        CrmDecideOnLoading(
            statusList = statusList,
            fetchLambda = viewModel::fetchStatusList,
            onUserIconClick = onUserIconClick,
            onNavigateToModulesPressed = onNavigateToModulesPressed,
            onStatusClick = viewModel::changeEmployeeStatus,
            onNewStatusCreated = viewModel::createNewStatus,
        )
    }

    @Composable
    private fun CrmDecideOnLoading(
        statusList: Result<List<Status>>,
        fetchLambda: () -> Unit,
        onUserIconClick: () -> Unit = {},
        onNavigateToModulesPressed: () -> Unit,
        onStatusClick: (Employee, Status) -> Unit,
        onNewStatusCreated: (String) -> Unit,
    ) {
        if (statusList is SuccessResult) {
            statusList.data?.let {
                RecruitmentLikeScreen(
                    userName = "Cool User",
                    statusList = it,
                    onUserIconClick = onUserIconClick,
                    onNavigateToModulesPressed = onNavigateToModulesPressed,
                    onStatusClick = onStatusClick,
                    onNewStatusCreated = onNewStatusCreated,
                    searchHintRes = R.string.crm_search_hint,
                )
            }
        } else {
            LoadingScreen(fetchLambda)
        }
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
                        )
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
                searchHintRes = R.string.crm_search_hint,
            )
        }
    }
}