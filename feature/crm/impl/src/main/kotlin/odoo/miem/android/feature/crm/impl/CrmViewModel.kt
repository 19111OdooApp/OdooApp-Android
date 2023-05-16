package odoo.miem.android.feature.crm.impl

import odoo.miem.android.common.network.crm.api.di.ICrmInteractorApi
import odoo.miem.android.common.network.crm.api.entities.kanban.OpportunityCRM
import odoo.miem.android.common.network.crm.api.entities.kanban.StatusCRM
import odoo.miem.android.common.network.selectingModules.api.di.ISelectingModulesInteractorApi
import odoo.miem.android.common.network.selectingModules.api.entities.User
import odoo.miem.android.core.di.impl.api
import odoo.miem.android.core.platform.presentation.BaseViewModel
import odoo.miem.android.core.utils.rx.lazyEmptyResultPublishSubject
import odoo.miem.android.core.utils.rx.onLoadingState
import odoo.miem.android.core.utils.state.ErrorResult
import odoo.miem.android.core.utils.state.ResultSubject
import timber.log.Timber

internal class CrmViewModel : BaseViewModel() {

    private val selectingModulesInteractor by api(ISelectingModulesInteractorApi::selectingModulesInteractor)

    private val crmInteractor by api(ICrmInteractorApi::crmInteractor)
    // TODO Return
//    private val сrmDetailsInteractor by api(ICrmInteractorApi::сrmDetailsInteractor)

    val statusState: ResultSubject<List<StatusCRM>> by lazyEmptyResultPublishSubject()
    val changeStatusState: ResultSubject<Boolean> by lazyEmptyResultPublishSubject()

    val userInfoState: ResultSubject<User> by lazyEmptyResultPublishSubject()

    /**
     * CRM Kanban
     */
    fun onOpenKanban() {
        statusState.onLoadingState()
        userInfoState.onLoadingState()

        selectingModulesInteractor
            .getUserInfo()
            .flatMap { user ->
                Timber.d("getUserInfo(): user - $user")
                userInfoState.onNext(user)
                crmInteractor.getCrmKanbanInfo(checkNotNull(user.data?.uid))
            }
            .schedule(
                crmKanbanFetchStatusChannel,
                onSuccess = { result ->
                    Timber.d("onOpenKanban(): result = $result")
                    statusState.onNext(result)
                },
                onError = Timber::e
            )
    }

    fun changeEmployeeStatus(opportunity: OpportunityCRM, status: StatusCRM) {
        crmInteractor
            .changeStageInCrmKanban(
                stageId = status.id,
                opportunityId = opportunity.id
            )
            .schedule(
                crmKanbanChangeStatusChannel,
                onSuccess = { result ->
                    Timber.d("changeEmployeeStatus(): result - $result")
                    if (result is ErrorResult) {
                        changeStatusState.onNext(ErrorResult(R.string.error_crm_change_status))
                    } else {
                        onOpenKanban()
                        changeStatusState.onNext(result)
                    }
                },
                onError = Timber::e
            )
    }

    fun createNewStatus(topic: String) {
        Timber.d("createNewStatus()")

        statusState.onLoadingState()
        crmInteractor
            .createNewCrmStatus(topic)
            .schedule(
                crmKanbanCreateStatusChannel,
                onSuccess = { status ->
                    Timber.d("createNewStatus(): list - ${status.data}")
                    onOpenKanban()
                },
                onError = Timber::e
            )
    }

    companion object {

        // Kanban
        val crmKanbanFetchStatusChannel = Channel()
        val crmKanbanChangeStatusChannel = Channel()
        val crmKanbanCreateStatusChannel = Channel()
    }
}
