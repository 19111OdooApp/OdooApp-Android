package odoo.miem.android.feature.recruitment.impl.screen.jobs.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.buttons.FilledTextButton
import odoo.miem.android.common.uiKitComponents.swither.Switcher
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooGray
import odoo.miem.android.core.uiKitTheme.odooOnGray
import odoo.miem.android.feature.recruitment.impl.R

@Suppress("MagicNumber")
@Composable
internal fun ColumnScope.RecruitmentJobsSheet(
    jobLink: String,
    isRecruitingDone: Boolean = false,
    isPublished: Boolean = false,
    onRecruitClick: (isActiveFirst: Boolean) -> Unit = {},
    onPublishedClick: (isActiveFirst: Boolean) -> Unit = {},
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val width = (LocalConfiguration.current.screenWidthDp * 0.125).dp
    val topPadding = 12.dp
    val switcherHeight = 56.dp
    val switcherWidth = LocalConfiguration.current.screenWidthDp.dp / 2

    Spacer(modifier = Modifier.height(topPadding * 2))

    Divider(
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .width(width)
            .align(Alignment.CenterHorizontally)
    )

    Spacer(modifier = Modifier.height(topPadding))

    SubtitleText(
        text = stringResource(R.string.recruitment_job_details),
        isLarge = true,
        modifier = Modifier.align(Alignment.CenterHorizontally),
    )

    Spacer(modifier = Modifier.height(topPadding))

    Switcher(
        firstStateText = stringResource(R.string.recruitment_job_recruit_start),
        secondStateText = stringResource(R.string.recruitment_job_recruit_done),
        isActiveFirst = !isRecruitingDone,
        width = switcherWidth,
        height = switcherHeight,
        padding = mainHorizontalPadding
    ) {
        onRecruitClick(it)
    }

    Spacer(modifier = Modifier.height(topPadding))

    Switcher(
        firstStateText = stringResource(R.string.recruitment_job_not_published),
        secondStateText = stringResource(R.string.recruitment_job_published),
        isActiveFirst = !isPublished,
        width = switcherWidth,
        height = switcherHeight,
        padding = mainHorizontalPadding
    ) {
        onPublishedClick(it)
    }

    Spacer(modifier = Modifier.height(topPadding))

    FilledTextButton(
        onClick = { clipboardManager.setText(AnnotatedString(jobLink)) },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            disabledContainerColor = odooGray,
            disabledContentColor = odooOnGray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = mainHorizontalPadding),
        textResource = R.string.recruitment_job_link
    )

    Spacer(modifier = Modifier.height(topPadding))
}
