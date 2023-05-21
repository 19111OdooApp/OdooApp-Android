package odoo.miem.android.feature.navigation.api.utils

import androidx.navigation.NavController
import odoo.miem.android.feature.navigation.api.data.Routes

fun NavController.navigateToUserProfile(
    userId: Long? = null
) = navigate("${Routes.userProfile}/${userId ?: -1}")
