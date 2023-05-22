package odoo.miem.android.common.network.recruitment.api.entities.details

data class ApplicationInfo(

    // Employee Info
    val id: Long,

    val stageInfo: List<Any>?,

    val employeeName: String?,

    val employeeEmail: String?,

    val employeePhone: String?,

    val employeeMobile: String?,

    val employeeFullName: String?,

    val employeeTestTask: String?,

    val employeeProject: String?,

    val employeeGroup: String?,

    val employeeSpecialization: String?,

    // Recruit Info
    val recruiterName: String?,

    val rating: Double,

    val recruiterSourceName: String?,

    // Work Info
    val jobName: String?,

    val departmentName: String?,

    // Payment Info
    val salaryExpected: Double?,

    val salaryProposed: Double?,

    // Employee summary
    val employeeSummary: String?,

    // Activity & message ids
    val activityIds: List<Long>?,

    val messageIds: List<Long>?,

    val logNotes: List<LogNoteInfo> = emptyList(),
)
