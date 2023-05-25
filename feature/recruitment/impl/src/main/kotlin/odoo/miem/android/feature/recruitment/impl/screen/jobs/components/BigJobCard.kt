package odoo.miem.android.feature.recruitment.impl.screen.jobs.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import odoo.miem.android.common.network.recruitment.api.entities.jobs.RecruitmentJob
import odoo.miem.android.common.uiKitComponents.R
import odoo.miem.android.common.uiKitComponents.utils.conditional
import odoo.miem.android.common.uiKitComponents.utils.drawDiagonalLabel
import odoo.miem.android.common.uiKitComponents.utils.getBackgroundColorCard
import odoo.miem.android.common.uiKitComponents.utils.glowEffect

/**
 * [BigJobCard] is implementation of big card for recruitment jobs
 *
 * @param job is current Job DTO
 * @param biggestNumber to show
 *
 * @author Vorozhtsov Mikhail
 */
@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun BigJobCard(
    job: RecruitmentJob,
    biggestNumber: Int = 1000,
    modifier: Modifier = Modifier,
    onLikeClick: (job: RecruitmentJob) -> Unit = {},
    onClick: (job: RecruitmentJob) -> Unit = {},
    onLongClick: (job: RecruitmentJob) -> Unit = {}
) = Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
        containerColor = job.name.getBackgroundColorCard(),
    ),
    modifier = modifier
        .fillMaxWidth()
        .height(240.dp)
        .conditional(job.isPublished) {
            drawDiagonalLabel(
                text = "Published",
                color = Color.White
            )
        }
        .combinedClickable(
            onClick = { onClick(job) },
            onLongClick = { onLongClick(job) }
        )
) {
    val horizontalPadding = 36.dp
    val topPadding = 12.dp

    Spacer(modifier = Modifier.height(topPadding))

    IconButton(
        onClick = { onLikeClick(job) },
        modifier = Modifier
            .size(45.dp)
            .align(Alignment.End)
            .padding(end = horizontalPadding / 2)
    ) {
        Icon(
            imageVector = if (job.isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.glowEffect(job.isFavorite)
        )
    }

    Text(
        text = job.name,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        maxLines = 2,
        overflow = TextOverflow.Clip,
        modifier = Modifier
            .align(Alignment.Start)
            .padding(horizontal = horizontalPadding)
    )

    Spacer(modifier = Modifier.height(topPadding))

    Text(
        text = pluralStringResource(
            id = R.plurals.job_number_of_new_application,
            count = job.numberOfNewApplication
        ).format(job.numberOfNewApplication),
        style = MaterialTheme.typography.titleSmall.copy(
            textDecoration = TextDecoration.Underline
        ),
        color = Color.White,
        modifier = Modifier
            .align(Alignment.Start)
            .padding(start = horizontalPadding)
    )

    Spacer(modifier = Modifier.height(topPadding * 2))

    Row(
        modifier = Modifier
            .padding(horizontal = horizontalPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MetaText(
            text = if (job.numberOfApplication > biggestNumber) {
                stringResource(id = R.string.job_big_number_of_application)
            } else {
                pluralStringResource(
                    id = R.plurals.job_number_of_application,
                    count = job.numberOfApplication
                ).format(job.numberOfApplication)
            }
        )
        MetaText(
            text = if (job.numberToRecruit > biggestNumber) {
                stringResource(id = R.string.job_big_number_to_recruit)
            } else {
                stringResource(
                    id = R.string.job_number_to_recruit
                ).format(job.numberToRecruit)
            }
        )
    }

    Spacer(modifier = Modifier.height(topPadding))
}

@Composable
private fun MetaText(
    text: String
) = Text(
    text = text,
    style = MaterialTheme.typography.bodyMedium.copy(
        fontSize = 14.sp
    ),
    color = Color.White,
    maxLines = 1
)
