package odoo.miem.android.feature.authorization.base.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import odoo.miem.android.core.uiKitTheme.odooHeaderGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.feature.authorization.base.api.IAuthorizationScreen

/**
 * [AuthorizationScreen] реализация интерфейса [IAuthorizationScreen]
 *
 * Методы по назначению:
 * - [AuthorizationScreen] - входная точка в этот экран, нужен для начальных инициализация.
 * Например, инициализация viewModel
 * - [AuthorizationScreenContent] - непосредственно верстка данного экрана
 * - [AuthorizationScreenPreview] - превью верстки, которая получилась в [AuthorizationScreenContent]
 *
 * @author Ворожцов Михаил
 */
class AuthorizationScreen : IAuthorizationScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun AuthorizationScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {
        AuthorizationScreenContent()
    }

    @Composable
    private fun AuthorizationScreenContent(

    ) = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
    ) {

        Image(
            painter = painterResource(R.drawable.logo_odoo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 35.dp)
                .size(63.dp, 20.dp),
        )
        
        Text(
            text = stringResource(R.string.login_welcome_header),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    start = 24.dp,
                    top = 31.dp
                )
        )

        Text(
            text = stringResource(R.string.login_welcome_text),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    start = 24.dp,
                    top = 11.dp
                ),
            color = odooHeaderGray
        )
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
    private fun AuthorizationScreenPreview() = OdooMiemAndroidTheme {
        AuthorizationScreenContent()
    }
}
