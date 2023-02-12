package odoo.miem.android.feature.profile.impl.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.core.uiKitTheme.odooOnGray

@Composable
fun ProfileHeader(
    userName: String,
    userEmail: String,
    userPhone: String,
    navigateBack: () -> Unit = {}
) {
    val heightPadding = 8.dp

    SimpleLogoAppBar(
        onBackButtonClick = navigateBack
    )

    Spacer(modifier = Modifier.height(heightPadding * 4))

    Text(
        text = userName.takeIf { it.isNotEmpty() } ?: stringResource(R.string.default_user_name),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSecondaryContainer
    )

    Spacer(modifier = Modifier.height(heightPadding))

    Text(
        text = userEmail.takeIf { it.isNotEmpty() } ?: stringResource(R.string.default_user_email),
        style = MaterialTheme.typography.titleSmall,
        textDecoration = TextDecoration.Underline,
        color = odooOnGray
    )

    Spacer(modifier = Modifier.height(heightPadding))

    Text(
        text = userPhone.takeIf { it.isNotEmpty() } ?: stringResource(R.string.default_user_phone),
        style = MaterialTheme.typography.titleSmall,
        color = odooOnGray
    )
}