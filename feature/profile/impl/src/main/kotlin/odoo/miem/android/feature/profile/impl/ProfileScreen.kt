package odoo.miem.android.feature.profile.impl

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import odoo.miem.android.feature.profile.api.IProfileScreen
import javax.inject.Inject

/**
 * [ProfileScreen] - implementation of [IProfileScreen]
 *
 * @author Vorozhtsov Mikhail
 */
class ProfileScreen @Inject constructor() : IProfileScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun ProfileScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {

    }

    @Composable
    private fun ProfileScreenContent() {}

    @Composable
    private fun ProfileScreenPreview() {}
}