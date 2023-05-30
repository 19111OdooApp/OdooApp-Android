
import odoo.miem.android.core.networkApi.crm.api.entities.CrmKanbanStagesResponse
import odoo.miem.android.core.networkApi.crm.api.entities.CrmResponse

object CrmTestData {

    val crmResponse = CrmResponse(
        length = 3,
        records = listOf(
            CrmResponse.Record(
                id = 1,
                rating = "1",
                stageInfo = listOf(1, "Cool stage 1"),
                creatorInfo = listOf(1, "Cool user 1"),
                partnerNameInfo = listOf(1, "Cool partner name 1"),
                name = "Test name 1",
                email = "Test email 1",
                summary = "Test summary 1",
                expectedRevenue = 100.0,
                companyCurrencyInfo = listOf(1, "$"),
                activityState = "overdue",
            ),
            CrmResponse.Record(
                id = 2,
                rating = "2",
                stageInfo = listOf(2, "Cool stage 2"),
                creatorInfo = listOf(2, "Cool user 2"),
                partnerNameInfo = listOf(2, "Cool partner name 2"),
                name = "Test name 2",
                email = "Test email 2",
                summary = "Test summary 2",
                expectedRevenue = 200.0,
                companyCurrencyInfo = listOf(2, "$"),
                activityState = "overdue",
            ),
            CrmResponse.Record(
                id = 3,
                rating = "3",
                stageInfo = listOf(3, "Cool stage 3"),
                creatorInfo = listOf(3, "Cool user 3"),
                partnerNameInfo = listOf(1, "Cool partner name 3"),
                name = "Test name 3",
                email = "Test email 3",
                summary = "Test summary 3",
                expectedRevenue = 300.0,
                companyCurrencyInfo = listOf(3, "$"),
                activityState = "overdue",
            ),
        )
    )

    val crmKanbanStagesResponse = CrmKanbanStagesResponse(
        stages = crmResponse.records?.map {
            CrmKanbanStagesResponse.Stage(
                stageInfo = it.stageInfo
            )
        }
    )
}