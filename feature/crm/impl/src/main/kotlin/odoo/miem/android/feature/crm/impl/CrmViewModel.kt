package odoo.miem.android.feature.crm.impl

import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.DeadlineStatus
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.rx.emptyResultBehaviorSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.StateResultSubject
import odoo.miem.android.core.utils.state.SuccessResult
import odoo.miem.android.feature.crm.impl.data.Employee
import odoo.miem.android.feature.crm.impl.data.Status

internal class CrmViewModel : BaseViewModel() {

    val statusState: StateResultSubject<List<Status>> = emptyResultBehaviorSubject()
    val picturesState: StateResultSubject<List<String>> = emptyResultBehaviorSubject()

    @Suppress("MagicNumber") // TODO: Remove once implemented
    fun fetchStatusList() {
        // TODO: Fetch actual data

        statusState.onLoadingState()
        picturesState.onNext(
            SuccessResult(
                listOf(
                    "https://yt3.googleusercontent.com/ytc/AL5GRJWDJvCQYGY8n6BT_f7DzaJCcGRJ69NY9I" +
                        "PU4G-K4Q=s900-c-k-c0x00ffffff-no-rj",
                    "https://yt3.googleusercontent.com/ytc/AL5GRJWDJvCQYGY8n6BT_f7DzaJCcGRJ69N" +
                        "Y9IPU4G-K4Q=s900-c-k-c0x00ffffff-no-rj",
                )
            )
        )
        statusState.onNext(
            SuccessResult(
                listOf(
                    Status(
                        "1",
                        listOf(
                            Employee(
                                "anna",
                                2.0,
                                null,
                                null,
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
                                null,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "alex",
                                2.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "alex",
                                2.0,
                                null,
                                null,
                                DeadlineStatus.EXPIRED
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                null,
                                DeadlineStatus.ACTIVE
                            ),
                            Employee(
                                "alex",
                                2.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            ),
                            Employee(
                                "misha",
                                3.0,
                                null,
                                null,
                                DeadlineStatus.NO_TASKS
                            )
                        ),
                    )
                )
            )
        )
    }

    @Suppress("UnusedPrivateMember") // TODO: Remove once implemented
    fun changeEmployeeStatus(employee: Employee, status: Status) {
        // TODO: Add change of status logic and update statusState
    }

    @Suppress("UnusedPrivateMember") // TODO: Remove once implemented
    fun createNewStatus(statusName: String) {
        // TODO: Add create status logic
    }
}
