package odoo.miem.android.feature.selectingModules.impl.moduleNotFoundScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.text.DisplayText
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.common.uiKitComponents.text.SubTitleText
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.feature.selectingModules.impl.R
// TODO MOVE TO SEPARATE MODULE
/**
 * [ModuleNotFoundScreen] - support screen for [SelectingModulesScreen]
 * appears if selected module is not implemented yet
 *
 * @author Egor Danilov
 */
@Composable
fun ModuleNotFoundScreen(
    onBackButtonClick: () -> Unit
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {
    val leadingIconButton = @Composable {
        IconButton(onClick = onBackButtonClick) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
    }

    SimpleLogoAppBar(
        navigationIcon = leadingIconButton
    )

    Spacer(modifier = Modifier.height(170.dp))

    DisplayText(
        textRes = R.string.sorry,
        color = MaterialTheme.colorScheme.primary
    )

    Spacer(modifier = Modifier.height(commonPadding))

    LabelText(
        textRes = R.string.module_not_found,
        isLarge = true
    )

    Spacer(modifier = Modifier.height(commonPadding))

    SubTitleText(
        textRes = R.string.sorry_desc,
        color = MaterialTheme.colorScheme.tertiary,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 36.dp)
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
private fun ModuleNotFoundScreenPreview() = OdooMiemAndroidTheme {
    ModuleNotFoundScreen {}
}
