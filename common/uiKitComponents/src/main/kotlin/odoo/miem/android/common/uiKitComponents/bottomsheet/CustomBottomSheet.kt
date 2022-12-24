package odoo.miem.android.common.uiKitComponents.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Custom reference of [BottomSheetScaffold]
 *
 * New param:
 * - [halfCoefficient] - the overlap coefficient of the screen in the [CustomBottomSheetValue.Half] state
 *
 * @author Vorozhtsov Mikhail
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomBottomSheetScaffold(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: CustomBottomSheetScaffoldState = rememberCustomBottomSheetScaffoldState(),
    topBar: (@Composable () -> Unit)? = null,
    sheetGesturesEnabled: Boolean = true,
    sheetShape: Shape = MaterialTheme.shapes.large,
    sheetElevation: Dp = BottomSheetScaffoldDefaults.SheetElevation,
    sheetBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    sheetPeekHeight: Dp = BottomSheetScaffoldDefaults.SheetPeekHeight,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    halfCoefficient: Float = 0.5F,
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()
    BoxWithConstraints(modifier) {
        val fullHeight = constraints.maxHeight.toFloat()
        val peekHeightPx = with(LocalDensity.current) { sheetPeekHeight.toPx() }
        var bottomSheetHeight by remember { mutableStateOf(fullHeight) }

        val swipeable = Modifier
            .nestedScroll(scaffoldState.customBottomSheetState.nestedScrollConnection)
            .swipeable(
                state = scaffoldState.customBottomSheetState,
                anchors = mapOf(
                    fullHeight - peekHeightPx to CustomBottomSheetValue.Collapsed,
                    fullHeight - bottomSheetHeight * halfCoefficient to CustomBottomSheetValue.Half,
                    fullHeight - bottomSheetHeight to CustomBottomSheetValue.Expanded
                ),
                orientation = Orientation.Vertical,
                enabled = sheetGesturesEnabled,
                resistance = null
            )
            .semantics {
                if (peekHeightPx != bottomSheetHeight) {
                    when {
                        scaffoldState.customBottomSheetState.isCollapsed -> {
                            expand {
                                scope.launch { scaffoldState.customBottomSheetState.expand() }
                                true
                            }
                        }
                        scaffoldState.customBottomSheetState.isHalf -> {
                            expand {
                                scope.launch { scaffoldState.customBottomSheetState.upToHalf() }
                                true
                            }
                        }
                        scaffoldState.customBottomSheetState.isExpanded -> {
                            collapse {
                                scope.launch { scaffoldState.customBottomSheetState.collapse() }
                                true
                            }
                        }
                    }
                }
            }

        CustomBottomSheetScaffoldStack(
            body = {
                Surface(
                    color = backgroundColor,
                    contentColor = contentColor
                ) {
                    Column(Modifier.fillMaxSize()) {
                        topBar?.invoke()
                        content(PaddingValues(bottom = sheetPeekHeight))
                    }
                }
            },
            bottomSheet = {
                Surface(
                    swipeable
                        .fillMaxWidth()
                        .requiredHeightIn(min = sheetPeekHeight)
                        .onGloballyPositioned {
                            bottomSheetHeight = it.size.height.toFloat()
                        },
                    shape = sheetShape,
                    elevation = sheetElevation,
                    border = BorderStroke(
                        width = 1.dp,
                        color = borderColor.copy(alpha = 0.5f)
                    ),
                    color = sheetBackgroundColor,
                    contentColor = sheetContentColor,
                    content = { Column(content = sheetContent) }
                )
            },
            bottomSheetOffset = scaffoldState.customBottomSheetState.offset
        )
    }
}

/**
 * Custom reference of [BottomSheetScaffoldStack]
 *
 * @author Vorozhtsov Mikhail
 */
@Composable
private fun CustomBottomSheetScaffoldStack(
    body: @Composable () -> Unit,
    bottomSheet: @Composable () -> Unit,
    bottomSheetOffset: State<Float>
) {
    Layout(
        content = {
            body()
            bottomSheet()
        }
    ) { measurables, constraints ->
        val placeable = measurables.first().measure(constraints)

        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)

            val (sheetPlaceable) =
                measurables.drop(1).map {
                    it.measure(constraints.copy(minWidth = 0, minHeight = 0))
                }

            val sheetOffsetY = bottomSheetOffset.value.roundToInt()

            sheetPlaceable.placeRelative(0, sheetOffsetY)
        }
    }
}
