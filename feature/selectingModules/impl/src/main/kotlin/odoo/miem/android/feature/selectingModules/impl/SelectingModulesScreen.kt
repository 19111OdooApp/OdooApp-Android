package odoo.miem.android.feature.selectingModules.impl

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import odoo.miem.android.common.uiKitComponents.appbars.SimpleLogoAppBar
import odoo.miem.android.common.uiKitComponents.cards.BigModuleCard
import odoo.miem.android.common.uiKitComponents.text.SubTitleText
import odoo.miem.android.common.uiKitComponents.text.TitleText
import odoo.miem.android.common.uiKitComponents.textfields.SearchTextField
import odoo.miem.android.core.uiKitTheme.OdooMiemAndroidTheme
import odoo.miem.android.core.uiKitTheme.mainHorizontalPadding
import odoo.miem.android.feature.selectingModules.api.ISelectingModulesScreen
import odoo.miem.android.feature.selectingModules.impl.data.OdooModule
import javax.inject.Inject
import kotlin.math.absoluteValue

/**
 * [SelectingModulesScreen] implementation of [ISelectingModulesScreen]
 *
 * Methods by purpose:
 * - [SelectingModulesScreen] - the input point to this screen is needed for initial initialization.
 * For example, ViewModel initialization
 * - [SelectingModulesScreenContent] - directly layout of this screen
 * - [SelectingModulesScreenPreview] - preview of the layout that turned out in [SelectingModulesScreenContent]
 *
 * @author Vorozhtsov Mikhail
 */
class SelectingModulesScreen @Inject constructor() : ISelectingModulesScreen {

    @SuppressLint("NotConstructor")
    @Composable
    override fun SelectingModulesScreen(
        navController: NavHostController,
        showMessage: (Int) -> Unit
    ) {

        // TODO Create base with loading handling
        SelectingModulesScreenContent(
            // TODO Delete Test Data
            favoriteModules = listOf(
                OdooModule(
                    name = "CRM",
                    numberOfNotifications = 1
                ),
                OdooModule(
                    name = "Recruitment",
                    numberOfNotifications = 5
                ),
                OdooModule(
                    name = "Pricing",
                    numberOfNotifications = 123
                ),
            )
        )
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun SelectingModulesScreenContent(
        favoriteModules: List<OdooModule> = emptyList() // TODO Not empty, always 1 card
    ) = Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        var searchInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }

        val pagerState = rememberPagerState()
        val haptic = LocalHapticFeedback.current
        var startTransaction by remember { mutableStateOf(false) }
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                if (startTransaction)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                else
                    startTransaction = true
            }
        }

        val topPadding = 24.dp


        SimpleLogoAppBar()

        SelectingModulesHeader(
            // TODO Add data
        )

        Spacer(modifier = Modifier.height(topPadding))

        TitleText(
            textRes = R.string.choose_module_text,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = mainHorizontalPadding)
        )

        Spacer(modifier = Modifier.height(topPadding * 0.4F))

        // TODO Search Transaction
        // https://github.com/mxalbert1996/compose-shared-elements
        // https://github.com/mobnetic/compose-shared-element
        SearchTextField(
            value = searchInput,
            onValueChange = { searchInput = it }
        )

        Spacer(modifier = Modifier.height(topPadding))

        // TODO Big Cards
        // Gradient - https://semicolonspace.com/jetpack-compose-circle-animation-gradient/

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
                    modifier = Modifier
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

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    vertical = topPadding,
                    horizontal = mainHorizontalPadding
                )
        )

        // TODO Bottom Sheet

    }

    @Composable
    private fun SelectingModulesHeader(
        userName: String = stringResource(R.string.default_user_name),
        avatarUrl: String? = null
    ) = Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = mainHorizontalPadding
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SubTitleText(textRes = R.string.hello_text)

            TitleText(text = userName, isLarge = false)
        }

        IconButton(
            onClick = { /*TODO Implement profile click*/ }
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(avatarUrl ?: R.drawable.default_user_avatar)
                        .apply {
                            error(R.drawable.default_user_avatar)
                            crossfade(true)
                        }
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }
    }

    @Composable
    @Preview(showBackground = true, backgroundColor = 0xFFFFFF)
    private fun SelectingModulesScreenPreview() = OdooMiemAndroidTheme {
        SelectingModulesScreenContent(
            favoriteModules = listOf(
                OdooModule(
                    name = "CRM",
                    numberOfNotifications = 1
                ),
                OdooModule(
                    name = "Recruitment",
                    numberOfNotifications = 5
                ),
                OdooModule(
                    name = "Pricing",
                    numberOfNotifications = 123
                ),
            )
        )
    }
}
