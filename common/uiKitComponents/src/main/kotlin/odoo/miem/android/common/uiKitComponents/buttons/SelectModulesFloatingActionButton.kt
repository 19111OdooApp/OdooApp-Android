package odoo.miem.android.common.uiKitComponents.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R

@Composable
fun SelectModulesFloatingActionButton(onClick: () -> Unit) =
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_select_modules),
            null,
            modifier = Modifier.size(55.dp),
            tint = Color.Unspecified
        )
    }
