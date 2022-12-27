package odoo.miem.android.common.uiKitComponents.appbars

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R

/**
 * [SimpleLogoAppBar] is base top app bar with Odoo logo
 *
 * @author Vorozhtsov Mikhail
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleLogoAppBar(
    modifier: Modifier = Modifier
) = CenterAlignedTopAppBar(
    title = {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.logo_odoo),
                contentDescription = stringResource(R.string.odoo_logo_desc),
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(26.dp)
            )
        }
    },
    modifier = modifier.statusBarsPadding()
)
