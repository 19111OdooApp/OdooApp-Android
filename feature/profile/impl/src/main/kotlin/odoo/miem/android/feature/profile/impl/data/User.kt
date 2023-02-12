package odoo.miem.android.feature.profile.impl.data

import java.util.*

// TODO Move, when will be logic
data class User(
    val name: String,
    val email: String,
    val phone: String,

    // TODO Maybe not include in user
    val job: Job,
    val contract: Contract,
    val applicationSummary: String
)

data class Job(
    val appliedJobName: String,
    val department: String,
    val recruiterProject: String,
    val group: String,
    val tags: String,
    val recruiter: String,
    val hireDate: Date,
    val appreciation: Double,
    val source: String,
    val testTask: String,
)

data class Contract(
    val expectedSalary: Float,
    val proposedSalary: Float
)