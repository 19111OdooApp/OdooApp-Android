package odoo.miem.android.common.uiKitComponents.switcher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.odooOnGray

@Composable
fun LazyColumnPageSwitcher(
    previousPageButtonEnabled: Boolean = true,
    nextPageButtonEnabled: Boolean = true,
    onPreviousPageClick: () -> Unit = {},
    onNextPageClick: () -> Unit = {},
    middleContent: @Composable () -> Unit = {}
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxWidth()
) {
    val previousPageButtonColor = if (previousPageButtonEnabled) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        odooOnGray
    }
    val nextPageButtonColor = if (nextPageButtonEnabled) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        odooOnGray
    }

    TextButton(
        onClick = onPreviousPageClick,
        enabled = previousPageButtonEnabled,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = odooOnGray
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_prev_page),
            contentDescription = null,
            tint = previousPageButtonColor
        )

        Spacer(modifier = Modifier.width(commonPadding / 2))

        LabelText(
            textRes = R.string.page_switcher_back,
            isMedium = false,
            textColor = previousPageButtonColor
        )
    }

    Spacer(modifier = Modifier.width(commonPadding))

    middleContent()

    Spacer(modifier = Modifier.width(commonPadding))

    TextButton(
        onClick = onNextPageClick,
        enabled = nextPageButtonEnabled,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = odooOnGray
        )
    ) {
        LabelText(
            textRes = R.string.page_switcher_forward,
            isMedium = false,
            textColor = nextPageButtonColor
        )

        Spacer(modifier = Modifier.width(commonPadding / 2))

        Icon(
            painter = painterResource(R.drawable.ic_next_page),
            contentDescription = null,
            tint = nextPageButtonColor
        )
    }
}