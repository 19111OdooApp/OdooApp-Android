package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.bottomsheet.create

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.rememberAsyncImagePainter
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.text.LargeHeadlineText
import odoo.miem.android.common.uiKitComponents.textfields.BaseTextField
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.halfMainVerticalPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding
import odoo.miem.android.core.uiKitTheme.odooOnButtonDisabled

const val GRID_CELLS = 4

@Composable
fun RecruitmentLikeCreateStatusBottomSheetContent(
    onCancelClick: () -> Unit,
    onDoneClick: (String, String) -> Unit,
    pictures: List<String>
) = Column(
    Modifier
        .fillMaxSize()
        .padding(horizontal = mainHorizontalPadding)
) {
    var statusName by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var imageLink by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = {
            statusName = TextFieldValue()
            imageLink = null
            focusManager.clearFocus()
            onCancelClick()
        }) {
            Text(
                text = stringResource(R.string.cancel_new_status_creation),
                color = MaterialTheme.colorScheme.primary,
            )
        }

        val isDoneEnabled = statusName.text.isNotBlank() && !imageLink.isNullOrEmpty()

        TextButton(
            onClick = {
                imageLink?.let { onDoneClick(statusName.text, it) }
                statusName = TextFieldValue()
                imageLink = null
                focusManager.clearFocus()
            },
            enabled = isDoneEnabled
        ) {
            Text(
                text = stringResource(R.string.finish_new_status_creation),
                color = if (isDoneEnabled) {
                    MaterialTheme.colorScheme.primary
                } else {
                    odooOnButtonDisabled
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
            painter = rememberAsyncImagePainter(
                model = imageLink,
                error = painterResource(id = R.drawable.add_plus)
            ),
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
            modifier = Modifier.align(Alignment.CenterHorizontally),
            labelResource = R.string.create_status_hint,
        )

        Spacer(modifier = Modifier.size(mainVerticalPadding))
    }

    Spacer(modifier = Modifier.size(mainVerticalPadding))

    Box(
        modifier = Modifier.border(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_CELLS),
            contentPadding = PaddingValues(commonPadding),
            modifier = Modifier.padding(all = mainHorizontalPadding)
        ) {
            items(pictures) {
                Box(modifier = Modifier.size(50.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(model = it),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            imageLink = it
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun asd() {
    RecruitmentLikeCreateStatusBottomSheetContent(
        onCancelClick = { /*TODO*/ },
        onDoneClick = { _, _ -> },
        pictures = listOf(
            "https://yt3.googleusercontent.com/ytc/AL5GRJWDJvCQYGY8n6BT_f7DzaJCcGRJ69N" +
                "Y9IPU4G-K4Q=s900-c-k-c0x00ffffff-no-rj",
            "https://yt3.googleusercontent.com/ytc/AL5GRJWDJvCQYGY8n6BT_f7DzaJCcGRJ69N" +
                "Y9IPU4G-K4Q=s900-c-k-c0x00ffffff-no-rj"
        )
    )
}
