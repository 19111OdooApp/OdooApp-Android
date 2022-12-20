package odoo.miem.android.feature.selectingModules.impl.components

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
import odoo.miem.android.common.uiKitComponents.cards.BigModuleCard
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.feature.selectingModules.impl.data.OdooModule
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun SelectingModulesFavoriteList(
    favoriteModules: List<OdooModule> = emptyList(), // TODO Don't forget to handle adding card
    indicatorModifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val pagerState = rememberPagerState()
    var startTransaction by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (startTransaction)
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            else
                startTransaction = true
        }
    }

    HorizontalPager(
        count = favoriteModules.size,
        contentPadding = PaddingValues(horizontal = mainHorizontalPadding),
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        with(favoriteModules[page]) {
            // TODO Should depence on input data?
            var isLikedState by remember { mutableStateOf(isLiked) }

            BigModuleCard(
                moduleName = name,
                numberOfNotification = numberOfNotifications,
                isLiked = isLikedState,
                onLikeClick = { isLikedState = !isLikedState },
                modifier = androidx.compose.ui.Modifier
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