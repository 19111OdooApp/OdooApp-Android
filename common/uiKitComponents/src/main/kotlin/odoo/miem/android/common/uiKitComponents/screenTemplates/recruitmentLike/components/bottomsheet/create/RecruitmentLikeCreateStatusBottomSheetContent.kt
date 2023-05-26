package odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.components.bottomsheet.create

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.text.LargeHeadlineText
import odoo.miem.android.common.uiKitComponents.textfields.BaseTextField
import odoo.miem.android.core.uiKitTheme.halfMainVerticalPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimaryGray

@Composable
fun RecruitmentLikeCreateStatusBottomSheetContent(
    onCancelClick: () -> Unit,
    onDoneClick: (statusName: String) -> Unit,
    @DrawableRes iconRes: Int
) = Column(
    Modifier
        .fillMaxSize()
        .padding(horizontal = mainHorizontalPadding)
) {
    var statusName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    val focusManager = LocalFocusManager.current
    val isDoneEnabled = statusName.text.isNotBlank()

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = {
            statusName = TextFieldValue()
            focusManager.clearFocus()
            onCancelClick()
        }) {
            Text(
                text = stringResource(R.string.cancel_new_status_creation),
                color = MaterialTheme.colorScheme.primary,
            )
        }

        TextButton(
            onClick = {
                statusName = TextFieldValue()
                focusManager.clearFocus()
                onDoneClick(statusName.text)
            },
            enabled = isDoneEnabled
        ) {
            Text(
                text = stringResource(R.string.finish_new_status_creation),
                color = if (isDoneEnabled) {
                    MaterialTheme.colorScheme.primary
                } else {
                    odooPrimaryGray
                },
            )
        }
    }

    LargeHeadlineText(
        text = stringResource(R.string.create_new_status_headline),
        modifier = Modifier
            .padding(vertical = halfMainVerticalPadding)
            .align(Alignment.Start),
    )

    Column(
        modifier = Modifier
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .padding(top = mainVerticalPadding)
        )

        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(
            modifier = Modifier
                .padding(top = mainVerticalPadding)
        )

        BaseTextField(
            value = statusName,
            onValueChange = { statusName = it },
            modifier = Modifier
                .padding(horizontal = mainHorizontalPadding)
                .align(Alignment.CenterHorizontally),
            labelResource = R.string.create_status_hint,
        )

        Spacer(modifier = Modifier.size(mainVerticalPadding))
    }
}

@Composable
@Preview
fun asd() {
    RecruitmentLikeCreateStatusBottomSheetContent(
        onCancelClick = {},
        onDoneClick = {},
        iconRes = R.drawable.add_plus
    )
}
