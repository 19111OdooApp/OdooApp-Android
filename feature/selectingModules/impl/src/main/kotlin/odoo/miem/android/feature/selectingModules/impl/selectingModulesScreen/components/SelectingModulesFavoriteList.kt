package odoo.miem.android.feature.selectingModules.impl.selectingModulesScreen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import odoo.miem.android.common.network.selectingModules.api.entities.OdooModule
import odoo.miem.android.common.uiKitComponents.cards.BigModuleCard
import odoo.miem.android.common.uiKitComponents.cards.BigModuleOutlinedCard
import odoo.miem.android.core.uiKitTheme.gradientColors
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import kotlin.math.absoluteValue

/**
 * [SelectingModulesFavoriteList] creates horizontal list of
 * favorite modules with [BigModuleCard] and [BigModuleOutlinedCard]
 *
 * @param favoriteModules is list of favorite modules
 * @param onAddModuleCardClick is action, when user click on add card
 *
 * @author Vorozhtsov Mikhail
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun SelectingModulesFavoriteList(
    favoriteModules: List<OdooModule> = emptyList(),
    indicatorModifier: Modifier = Modifier,
    onLikeModuleClick: (OdooModule) -> Unit = {},
    onModuleCardClick: () -> Unit = {},
    onAddModuleCardClick: () -> Unit = {}
) {
    val haptic = LocalHapticFeedback.current
    val pagerState = rememberPagerState()
    var startTransaction by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            if (startTransaction) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            } else {
                startTransaction = true
            }
        }
    }

    HorizontalPager(
        count = favoriteModules.size + 1,
        contentPadding = PaddingValues(horizontal = mainHorizontalPadding),
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        val bigCardModifier = Modifier
            .graphicsLayer {
                // Calculate the absolute offset for the current page from the
                // scroll position. We use the absolute value which allows us to mirror
                // any effects for both directions
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                // We animate the scaleX + scaleY, between 85% and 100%
                lerp(
                    start = 0.93f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }

                // We animate the alpha, between 50% and 100%
                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            }

        if (page != favoriteModules.size) {
            with(favoriteModules[page]) {
                // TODO Should depend on input data?
                var isLikedState by remember { mutableStateOf(isFavourite) }

                BigModuleCard(
                    moduleName = name,
                    numberOfNotification = numberOfNotifications,
                    isLiked = isLikedState,
                    onClick = onModuleCardClick,
                    onLikeClick = {
                        isLikedState = !isLikedState
                        onLikeModuleClick(this)
                    },
                    modifier = bigCardModifier
                )
            }
        } else {
            BigModuleOutlinedCard(
                gradientColors = gradientColors,
                modifier = bigCardModifier,
                onClick = onAddModuleCardClick
            )
        }
    }

    Spacer(Modifier.height(24.dp))

    HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = indicatorModifier,
        activeColor = MaterialTheme.colorScheme.onTertiary,
        inactiveColor = MaterialTheme.colorScheme.tertiary
    )
}
