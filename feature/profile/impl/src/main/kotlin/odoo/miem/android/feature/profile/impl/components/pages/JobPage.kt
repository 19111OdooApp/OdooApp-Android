package odoo.miem.android.feature.profile.impl.components.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.feature.profile.impl.data.User
import odoo.miem.android.feature.profile.impl.R

@Composable
fun JobPage(
    user: User
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxWidth()
) {

    val heightPadding = 16.dp
    // TODO To resources
    TitleText(
        text = stringResource(R.string.title_job),
        isLarge = false,
        color = odooPrimary
    )

    Spacer(modifier = Modifier.height(heightPadding))

    textJobPageItem("Applied Job", "УЛ СВТ")

    textJobPageItem("Department", "")

    textJobPageItem("Recruter’s project", "")

    textJobPageItem("Person’s group", "")

    textJobPageItem("Tags", "")

    textJobPageItem("Recruiter", "Королев Денис Александрович")

    textJobPageItem("Hire Date", "10/06/2022  19:33:34")

//    baseJobPageItem("Appreciation", "10/06/2022  19:33:34")  TODO

    textJobPageItem("Source", "")

    textJobPageItem("Test task", "")

    Spacer(modifier = Modifier.height(heightPadding * 2))

    TitleText(
        text = stringResource(R.string.title_contract),
        isLarge = false,
        color = odooPrimary
    )

    Spacer(modifier = Modifier.height(heightPadding ))

    textJobPageItem("Expected Salary", "0.00") // TODO %f
    textJobPageItem("Proposed Salary", "0.00")
}

@Composable
private fun ColumnScope.textJobPageItem(
    key: String,
    value: String
) {
    baseJobPageItem(key) { modifier ->
        JobPageItemText(
            text = value,
            modifier = modifier.padding(start = 24.dp)
        )
    }
}


@Composable
private fun ColumnScope.baseJobPageItem(
    key: String,
    valueContent: @Composable (modifier: Modifier) -> Unit
) = Row(
    modifier = Modifier.height(32.dp),
    verticalAlignment = Alignment.CenterVertically,
) {

    JobPageItemText(
        text = key,
        modifier = Modifier.weight(0.4F)
    )

    Divider(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
    )

    valueContent(Modifier.weight(0.6F))
}

@Composable
private fun JobPageItemText(
    text: String,
    modifier: Modifier
) = Text(
    text = text,
    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
    overflow = TextOverflow.Ellipsis,
    maxLines = 1,
    modifier = modifier
)