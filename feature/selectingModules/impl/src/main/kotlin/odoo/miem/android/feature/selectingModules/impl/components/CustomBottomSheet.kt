package odoo.miem.android.feature.selectingModules.impl.components

import androidx.compose.animation.core.AnimationSpec
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
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.SwipeableState
import androidx.compose.material.contentColorFor
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// TODO Refactor to ui components
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
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    sheetPeekHeight: Dp = BottomSheetScaffoldDefaults.SheetPeekHeight,
    backgroundColor: Color = MaterialTheme.colors.background,
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
                    color = sheetBackgroundColor,
                    contentColor = sheetContentColor,
                    content = { Column(content = sheetContent) }
                )
            },
            bottomSheetOffset = scaffoldState.customBottomSheetState.offset
        )
    }
}

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

@ExperimentalMaterialApi
@Stable
class CustomBottomSheetScaffoldState(
    val customBottomSheetState: CustomBottomSheetState
)

@Composable
@ExperimentalMaterialApi
fun rememberCustomBottomSheetState(
    initialValue: CustomBottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (CustomBottomSheetValue) -> Boolean = { true }
): CustomBottomSheetState {
    return rememberSaveable(
        animationSpec,
        saver = CustomBottomSheetState.Saver(
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    ) {
        CustomBottomSheetState(
            initialValue = initialValue,
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    }
}

@Composable
@ExperimentalMaterialApi
fun rememberCustomBottomSheetScaffoldState(
    customBottomSheetState: CustomBottomSheetState = rememberCustomBottomSheetState(CustomBottomSheetValue.Collapsed),
): CustomBottomSheetScaffoldState {
    return remember(customBottomSheetState) {
        CustomBottomSheetScaffoldState(
            customBottomSheetState = customBottomSheetState
        )
    }
}

enum class CustomBottomSheetValue {
    /**
     * The bottom sheet is visible, but only showing its peek height.
     */
    Collapsed,

    Half,

    /**
     * The bottom sheet is visible at its maximum height.
     */
    Expanded
}

@OptIn(ExperimentalMaterialApi::class)
class CustomBottomSheetState(
    initialValue: CustomBottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (CustomBottomSheetValue) -> Boolean = { true }
) : SwipeableState<CustomBottomSheetValue>(
    initialValue = initialValue,
    animationSpec = animationSpec,
    confirmStateChange = confirmStateChange
) {
    /**
     * Whether the bottom sheet is expanded.
     */
    val isExpanded: Boolean
        get() = currentValue == CustomBottomSheetValue.Expanded

    val isHalf: Boolean
        get() = currentValue == CustomBottomSheetValue.Half

    /**
     * Whether the bottom sheet is collapsed.
     */
    val isCollapsed: Boolean
        get() = currentValue == CustomBottomSheetValue.Collapsed

    /**
     * Expand the bottom sheet with animation and suspend until it if fully expanded or animation
     * has been cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the expand animation ended
     */
    suspend fun expand() = animateTo(CustomBottomSheetValue.Expanded)

    suspend fun upToHalf() = animateTo(CustomBottomSheetValue.Half)

    /**
     * Collapse the bottom sheet with animation and suspend until it if fully collapsed or animation
     * has been cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the collapse animation ended
     */
    suspend fun collapse() = animateTo(CustomBottomSheetValue.Collapsed)

    companion object {
        /**
         * The default [Saver] implementation for [BottomSheetState].
         */
        fun Saver(
            animationSpec: AnimationSpec<Float>,
            confirmStateChange: (CustomBottomSheetValue) -> Boolean
        ): Saver<CustomBottomSheetState, *> = Saver(
            save = { it.currentValue },
            restore = {
                CustomBottomSheetState(
                    initialValue = it,
                    animationSpec = animationSpec,
                    confirmStateChange = confirmStateChange
                )
            }
        )
    }

    internal val nestedScrollConnection = this.PreUpPostDownNestedScrollConnection
}

@OptIn(ExperimentalMaterialApi::class)
internal val <T> SwipeableState<T>.PreUpPostDownNestedScrollConnection: NestedScrollConnection
    get() = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.toFloat()
            return if (delta < 0 && source == NestedScrollSource.Drag) {
                performDrag(delta).toOffset()
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return if (source == NestedScrollSource.Drag) {
                performDrag(available.toFloat()).toOffset()
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val toFling = Offset(available.x, available.y).toFloat()
            return if (toFling < 0 && offset.value > Float.NEGATIVE_INFINITY) {
                performFling(velocity = toFling)
                // since we go to the anchor with tween settling, consume all for the best UX
                available
            } else {
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            performFling(velocity = Offset(available.x, available.y).toFloat())
            return available
        }

        private fun Float.toOffset(): Offset = Offset(0f, this)

        private fun Offset.toFloat(): Float = this.y
    }