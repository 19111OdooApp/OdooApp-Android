package odoo.miem.android.common.uiKitComponents.bottomsheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CancellationException

/**
 * Custom reference of [BottomSheetState] with new [CustomBottomSheetValue.Half] state
 *
 * @see CustomBottomSheetScaffold
 *
 * @author Vorozhtsov Mikhail
 */
@OptIn(ExperimentalMaterialApi::class)
class CustomBottomSheetState(
    initialValue: CustomBottomSheetValue,
    val possibleValues: List<CustomBottomSheetValue>,
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
        get() = currentValue is CustomBottomSheetValue.Expanded

    val isHalf: Boolean
        get() = currentValue is CustomBottomSheetValue.Half

    /**
     * Whether the bottom sheet is collapsed.
     */
    val isCollapsed: Boolean
        get() = currentValue is CustomBottomSheetValue.Collapsed

    val isHidden: Boolean
        get() = currentValue is CustomBottomSheetValue.Hidden

    /**
     * Expand the bottom sheet with animation and suspend until it if fully expanded or animation
     * has been cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the expand animation ended
     */
    suspend fun expand() = animateTo(possibleValues.first { it is CustomBottomSheetValue.Expanded })

    suspend fun upToHalf() = animateTo(possibleValues.first { it is CustomBottomSheetValue.Half })

    suspend fun hide() = animateTo(possibleValues.first { it is CustomBottomSheetValue.Hidden })

    /**
     * Collapse the bottom sheet with animation and suspend until it if fully collapsed or animation
     * has been cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the collapse animation ended
     */
    suspend fun collapse() =
        animateTo(possibleValues.first { it is CustomBottomSheetValue.Collapsed })

    companion object {
        /**
         * The default [saver] implementation for [BottomSheetState].
         */
        fun saver(
            animationSpec: AnimationSpec<Float>,
            confirmStateChange: (CustomBottomSheetValue) -> Boolean,
        ): Saver<CustomBottomSheetState, *> = Saver(
            save = { it.currentValue to it.possibleValues },
            restore = {
                CustomBottomSheetState(
                    initialValue = it.first,
                    animationSpec = animationSpec,
                    confirmStateChange = confirmStateChange,
                    possibleValues = it.second,
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
