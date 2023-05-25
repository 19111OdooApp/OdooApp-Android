package odoo.miem.android.common.network.recruitment.api.entities.details

import odoo.miem.android.core.networkApi.recruitment.api.entities.RecruitmentLogNoteResponse

data class LogNoteInfo(

    val id: Long,

    val date: String?,

    val message: String?,

    val authorId: Long?,

    val authorName: String?,

    val postType: String?,

    val trackingInfoList: List<RecruitmentLogNoteResponse.TrackingInfo>,

    val subtypeDescription: String?,
)
