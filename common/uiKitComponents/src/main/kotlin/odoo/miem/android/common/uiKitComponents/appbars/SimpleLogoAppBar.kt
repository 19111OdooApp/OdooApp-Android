package odoo.miem.android.common.uiKitComponents.appbars

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleLogoAppBar(
    modifier: Modifier = Modifier
) = TopAppBar(
    title = {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo_odoo),
                contentDescription = stringResource(R.string.odoo_logo_desc),
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(30.dp)
            )
        }
    },
    modifier = modifier.statusBarsPadding()
)
