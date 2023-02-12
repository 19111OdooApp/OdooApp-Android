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
import odoo.miem.android.common.uiKitComponents.bar.RatingBar
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.odooPrimary
import odoo.miem.android.feature.profile.impl.data.User
import odoo.miem.android.feature.profile.impl.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun JobPage(
    user: User
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(horizontal = mainHorizontalPadding)
        .fillMaxSize()
) {
    val heightPadding = 16.dp
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    TitleText(
        text = stringResource(R.string.title_job),
        isLarge = false,
        color = odooPrimary
    )

    Spacer(modifier = Modifier.height(heightPadding))

    textJobPageItem(
        key = stringResource(R.string.item_applied_job),
        value = user.job.appliedJobName
    )

    textJobPageItem(
        key = stringResource(R.string.item_department),
        value = user.job.department
    )

    textJobPageItem(
        key = stringResource(R.string.item_recruites_project),
        value = user.job.recruiterProject
    )

    textJobPageItem(
        key = stringResource(R.string.item_persons_group),
        value = user.job.group
    )

    textJobPageItem(
        key = stringResource(R.string.item_tags),
        value = user.job.tags
    )

    textJobPageItem(
        key = stringResource(R.string.item_recruiter),
        value = user.job.recruiter
    )

    textJobPageItem(
        key = stringResource(R.string.item_hire_date),
        value = dateFormat.format(user.job.hireDate)
    )

    baseJobPageItem(stringResource(R.string.item_appreciation)) { modifier ->
        RatingBar(
            modifier = modifier,
            rating = user.job.appreciation,
            starsCount = 3
        )
    }

    textJobPageItem(
        key = stringResource(R.string.item_source),
        value = user.job.source
    )

    textJobPageItem(
        key = stringResource(R.string.item_test_task),
        value = user.job.testTask
    )

    Spacer(modifier = Modifier.height(heightPadding * 2))

    TitleText(
        text = stringResource(R.string.title_contract),
        isLarge = false,
        color = odooPrimary
    )

    Spacer(modifier = Modifier.height(heightPadding))

    textJobPageItem(
        key = stringResource(R.string.item_expected_salary),
        value = stringResource(R.string.item_salary).format(user.contract.expectedSalary)
    )

    textJobPageItem(
        key = stringResource(R.string.item_proposed_salary),
        value = stringResource(R.string.item_salary).format(user.contract.proposedSalary)
    )
}

@Composable
private fun ColumnScope.textJobPageItem(
    key: String,
    value: String
) {
    baseJobPageItem(key) { modifier ->
        JobPageItemText(
            text = value,
            modifier = modifier
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

    valueContent(
        Modifier
            .weight(0.6F)
            .padding(start = 24.dp)
    )
}

@Composable
private fun JobPageItemText(
    text: String,
    modifier: Modifier
) = Text(
    text = text,
    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
    color = MaterialTheme.colorScheme.onPrimary,
    overflow = TextOverflow.Ellipsis,
    maxLines = 1,
    modifier = modifier
)