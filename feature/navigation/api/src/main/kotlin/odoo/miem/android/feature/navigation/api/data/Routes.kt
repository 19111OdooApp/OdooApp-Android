package odoo.miem.android.feature.navigation.api.data

/**
 * Singleton предназаченный для хранения путей до конкретного экрана
 *
 * @author Ворожцов Михаил
 */
object Routes {
    const val authorization = "authorization"
    const val selectingModules = "selectingModules"
    const val moduleNotFound = "moduleNotFound"
    const val recruitmentKanban = "recruitmentKanban"
    const val recruitmentDetails = "recruitmentDetails"
    const val recruitmentJobs = "recruitmentJobs"
    const val crmKanban = "crmKanban"
    const val crmDetails = "crmDetails"
    const val userProfile = "userProfile"
    const val employees = "employees"
    const val employeeDetails = "employeeDetails"

    object Arguments {
        const val recruitmentJobId = "recruitmentJobId"
        const val recruitmentApplicationId = "recruitmentApplicationId"
        const val crmOpportunityId = "crmOpportunityId"
        const val userId = "userId"

        const val employeesEmployeeId = "employeesEmployeeId"
    }
}
