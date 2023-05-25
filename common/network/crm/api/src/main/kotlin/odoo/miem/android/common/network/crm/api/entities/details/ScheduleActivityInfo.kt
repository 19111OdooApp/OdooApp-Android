package odoo.miem.android.common.network.crm.api.entities.details

data class ScheduleActivityInfo(

    val id: Long,

    val activityName: String?,

    val note: String?,

    val createUserId: Long?,

    val createUserName: String?,

    val assignUserId: Long?,

    val assignUserName: String?,

    val createDate: String,

    val deadline: String,

    val state: String,
)
