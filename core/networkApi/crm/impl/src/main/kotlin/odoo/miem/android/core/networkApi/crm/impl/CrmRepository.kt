package odoo.miem.android.core.networkApi.crm.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.crm.api.ICrmRepository
import odoo.miem.android.core.networkApi.crm.api.entities.CrmApplicationDetailsResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmKanbanStagesResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmLogNoteResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmScheduleActivityResponse
import odoo.miem.android.core.networkApi.crm.impl.source.ICrmDetailsService
import odoo.miem.android.core.networkApi.crm.impl.source.ICrmService
import timber.log.Timber
import javax.inject.Inject

/**
 * [CrmRepository] - implementation of [IRecruitmentRepository]
 *
 * @author Vorozhtsov Mikhail
 */
class CrmRepository @Inject constructor() : ICrmRepository {

    private val crmService by jsonRpcApi<ICrmService>()
    private val crmDetailsService by jsonRpcApi<ICrmDetailsService>()

    /**
     * Crm Kanban
     */
    override fun getCrmKanbanInfo(userId: Int): Single<CrmResponse> {
        Timber.d("getCrmKanbanInfo()")

        return Single.fromCallable {
            crmService.getCrmInfo(
                domain = listOf(
                    "&",
                    listOf("type", "=", "opportunity"),
                    listOf("user_id", "=", userId),
                ),
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun getCrmKanbanStages(userId: Int): Single<CrmKanbanStagesResponse> {
        Timber.d("getCrmKanbanStages()")

        return Single.fromCallable {
            crmService.getCrmKanbanStages(
                kwargs = mapOf(
                    "domain" to listOf(
                        "&",
                        listOf("type", "=", "opportunity"),
                        listOf("user_id", "=", userId),
                    ),
                    "fields" to listOf("stage_id"),
                    "groupby" to listOf("stage_id"),
                )
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun createNewCrmStatus(topic: String): Single<List<Any>> {
        Timber.d("createNewCrmStatus()")

        return Single.fromCallable {
            crmService.createNewKanbanStatus(
                args = listOf(topic),
                kwargs = mapOf(
                    "context" to mapOf(
                        "default_type" to "opportunity"
                    )
                )
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun changeStageInCrmKanban(stageId: Long, opportunityId: Long): Single<Boolean> {
        Timber.d("changeStageInCrmKanban(): stageId - $stageId, opportunityId - $opportunityId")

        return Single.fromCallable {
            crmService.changeStageInCrmKanban(
                args = listOf(
                    listOf(opportunityId),
                    mapOf("stage_id" to stageId)
                ),
            )
        }.subscribeOn(Schedulers.io())
    }

    /**
     * Crm Opportunity Details
     */
    override fun getOpportunityInfo(opportunityId: Long): Single<CrmApplicationDetailsResponse> {
        Timber.d("getOpportunityInfo(): opportunityId - $opportunityId")

        return Single.fromCallable {
            crmDetailsService.getOpportunityInfo(
                args = listOf(
                    listOf(opportunityId),
                    opportunityInfoFields
                )
            ).first()
        }.subscribeOn(Schedulers.io())
    }

    override fun getLogNotes(opportunityId: Long): Single<List<CrmLogNoteResponse>> {
        Timber.d("getLogNotes(): userId - $opportunityId")

        return Single.fromCallable {
            crmDetailsService.getLogNotes(
                threadId = opportunityId
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun createLogNote(
        opportunityId: Long,
        text: String
    ): Single<Unit> {
        Timber.d("createLogNote(): opportunityId - $opportunityId, text - $text")

        return Single.fromCallable {
            crmDetailsService.createLogNote(
                postData = mapOf(
                    "body" to text,
                    "message_type" to "comment",
                    "subtype_xmlid" to "mail.mt_note"
                ),
                threadId = opportunityId
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun getScheduleActivities(activityIds: List<Long>): Single<List<CrmScheduleActivityResponse>> {
        Timber.d("getScheduleActivities(): activityIds - $activityIds")

        return Single.fromCallable {
            crmDetailsService.getScheduleActivities(
                args = listOf(activityIds)
            )
        }.subscribeOn(Schedulers.io())
    }

    private companion object {

        val opportunityInfoFields = listOf(
            "id",
            "stage_id",
            "partner_name",
            "name",
            "contact_name",

            "phone",
            "mobile",
            "email_from",

            "company_currency",
            "expected_revenue",
            "probability",

            "street",
            "city",
            "state_id",
            "zip",
            "country_id",

            "website",
            "date_deadline",
            "priority",

            "user_id",
            "team_id",
            "company_id",
            "description",

            "campaign_id",
            "medium_id",
            "source_id",

            "day_open",
            "day_close",

            "activity_ids",
            "message_ids",
        )
    }
}
