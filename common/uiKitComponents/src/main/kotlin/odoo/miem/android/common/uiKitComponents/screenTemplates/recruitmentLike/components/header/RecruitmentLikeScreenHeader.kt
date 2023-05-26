package odoo.miem.android.common.uiKitComponents.screenTemplates.recruitmentLike.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.headers.CommonModuleHeader
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding

@Composable
fun RecruitmentLikeScreenHeader(
    avatarUrl: String?,
    userName: String,
    title: String,
    onUserIconClick: () -> Unit = {}
) = Column {
    SimpleLogoAppBar(modifier = Modifier.background(Color.Black))

    CommonModuleHeader(
        userName = userName,
        avatarUrl = avatarUrl,
        onUserIconClick = onUserIconClick
    )

    TitleText(
        text = title,
        modifier = Modifier.padding(horizontal = mainHorizontalPadding)
    )
}
