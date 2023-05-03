package odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.components.bottomsheet.change

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeEmployeeModel
import odoo.miem.android.common.uiKitComponents.screen.recruitmentLike.model.RecruitmentLikeStatusModel
import odoo.miem.android.common.uiKitComponents.text.SubtitleText
import odoo.miem.android.common.uiKitComponents.utils.getStatusIcon
import odoo.miem.android.core.uiKitTheme.employeeCardSpacing
import odoo.miem.android.core.uiKitTheme.halfMainVerticalPadding
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.core.uiKitTheme.mainVerticalPadding

const val COLUMN_COUNT = 5

/**
 *
 *
 * @param S status model class
 * @param E employee model class
 * @param employee employee model class
 * @param statusList list of statuses
 * @param onCreateStatusClick
 * @param onStatusClicked
 */
@Composable
fun <S : RecruitmentLikeStatusModel<E>, E : RecruitmentLikeEmployeeModel>
RecruitmentLikeChangeStatusBottomSheetContent(
    employee: E,
    statusList: List<S>,
    onCreateStatusClick: () -> Unit,
    onStatusClicked: (E, S) -> Unit,
) =
    Column(modifier = Modifier.padding(horizontal = mainHorizontalPadding)) {
        Spacer(modifier = Modifier.padding(top = mainVerticalPadding))

        Box(
            modifier = Modifier
                .size(width = 38.dp, height = 3.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(CornerSize(2.dp)))
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(top = halfMainVerticalPadding))

        SubtitleText(text = stringResource(id = R.string.recruitment_change_status), isLarge = true)

        Spacer(modifier = Modifier.padding(top = mainVerticalPadding))

        LazyVerticalGrid(
            columns = GridCells.Fixed(COLUMN_COUNT),
            horizontalArrangement = Arrangement.spacedBy(employeeCardSpacing),
            modifier = Modifier.padding(bottom = halfMainVerticalPadding)
        ) {
            items(statusList) { status ->
                RecruitmentLikeBottomSheetElement(
                    onClick = { onStatusClicked(employee, status) },
                    painter = painterResource(getStatusIcon(status.id)),
                    name = status.statusName
                )
            }
            item {
                RecruitmentLikeBottomSheetElement(
                    onClick = onCreateStatusClick,
                    painter = painterResource(id = R.drawable.add_plus),
                    name = stringResource(id = R.string.recruitment_add_new_status)
                )
            }
        }
    }
