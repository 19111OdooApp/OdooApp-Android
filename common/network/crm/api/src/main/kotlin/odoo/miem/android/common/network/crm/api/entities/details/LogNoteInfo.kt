package odoo.miem.android.common.network.crm.api.entities.details

import odoo.miem.android.core.networkApi.crm.api.entities.CrmLogNoteResponse

data class LogNoteInfo(

    val id: Long,

    val date: String?,

    val message: String?,

    val authorId: Long?,

    val authorName: String?,

    val postType: String?,

    val trackingInfoList: List<CrmLogNoteResponse.TrackingInfo>,

    val subtypeDescription: String?,
)
