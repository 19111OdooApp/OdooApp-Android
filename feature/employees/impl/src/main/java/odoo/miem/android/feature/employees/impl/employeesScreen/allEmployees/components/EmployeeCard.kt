package odoo.miem.android.feature.employees.impl.employeesScreen.allEmployees.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import odoo.miem.android.common.network.employees.api.entities.EmployeeBasicInfo
import odoo.miem.android.common.uiKitComponents.spinner.DefaultCircleSpinner
import odoo.miem.android.common.uiKitComponents.text.HeadlineText
import odoo.miem.android.common.uiKitComponents.text.LabelText
import odoo.miem.android.common.utils.avatar.AvatarRequestHeader
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.commonPadding
import odoo.miem.android.core.uiKitTheme.odooGray

/**
 * [EmployeeCard] is implementation of small card for employees
 *
 * @param employee is current employee DTO
 *
 * @author Egor Danilov
 */
@Composable
fun EmployeeCard(
    employee: EmployeeBasicInfo,
    avatarRequestHeaders: List<AvatarRequestHeader>,
    modifier: Modifier = Modifier,
    onClick: (employee: EmployeeBasicInfo) -> Unit = {}
) = Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.background
    ),
    border = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.onPrimary
    ),
    modifier = modifier
        .clip(RoundedCornerShape(20.dp))
        .fillMaxWidth()
        .clickable { onClick(employee) }
) {
    val avatarSize = 90.dp
    val avatarShape = RoundedCornerShape(15.dp)

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(commonPadding)
    ) {
        Box(
            modifier = Modifier.size(avatarSize)
        ) {
            if (employee.avatarLink != null) {
                val model = ImageRequest.Builder(LocalContext.current)
                    .data(employee.avatarLink)
                    .apply {
                        avatarRequestHeaders.forEach {
                            this.addHeader(
                                name = it.name,
                                value = it.value
                            )
                        }
                    }
                    .size(Size.ORIGINAL)
                    .diskCacheKey("employee_avatar_${employee.id}")
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build()

                val painter = rememberAsyncImagePainter(model = model)

                when (painter.state) {
                    is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(avatarShape)
                                .size(avatarSize)
                        )
                    }
                    is AsyncImagePainter.State.Error -> {
                        Icon(
                            imageVector = Icons.Rounded.QuestionMark,
                            contentDescription = null,
                            tint = odooGray,
                            modifier = Modifier
                                .clip(avatarShape)
                                .size(avatarSize)
                        )
                    }
                    else -> {
                        DefaultCircleSpinner(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(36.dp)
                        )
                    }
                }
            } else {
                Icon(
                    imageVector = Icons.Rounded.QuestionMark,
                    contentDescription = null,
                    tint = odooGray,
                    modifier = Modifier
                        .clip(avatarShape)
                        .size(avatarSize)
                )
            }
        }

        Spacer(modifier = Modifier.width(commonPadding))

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            HeadlineText(
                text = employee.name.uppercase(),
                isMedium = false
            )

            Spacer(modifier = Modifier.height(6.dp))

            employee.job
                ?.takeIf { it.isNotEmpty() }
                ?.let {
                    LabelText(
                        text = it,
                        isMedium = false
                    )
                }

            employee.email
                ?.takeIf { it.isNotEmpty() }
                ?.let {
                    LabelText(
                        text = it,
                        isMedium = false
                    )
                }

            employee.phone
                ?.takeIf { it.isNotEmpty() }
                ?.let {
                    LabelText(
                        text = it,
                        isMedium = false
                    )
                }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
private fun EmployeeCardPreview() = OdooMiemAndroidTheme {
    EmployeeCard(
        employee = EmployeeBasicInfo(
            id = 0L,
            name = "Ranasinghe Mudiyanselage Pathma Kantha Kudagammana",
            job = null,
            email = "pranasingkhemduiyanselage@edu.hse.ru",
            phone = null,
            avatarLink = null,
        ),
        avatarRequestHeaders = emptyList(),
        modifier = Modifier.wrapContentHeight()
    )
}