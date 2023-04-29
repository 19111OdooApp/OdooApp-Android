package odoo.miem.android.common.uiKitComponents.bottomsheet

import java.io.Serializable

/**
 * Custom reference of [BottomSheetValue] with new [CustomBottomSheetValue.Half] state
 *
 * @see CustomBottomSheetScaffold
 *
 * @author Vorozhtsov Mikhail
 */
sealed class CustomBottomSheetValue : Serializable {

    abstract fun calculate(
        fullHeight: Float,
        bottomSheetHeight: Float,
    ): Float

    /**
     * The bottom sheet is visible, but only showing its peek height.
     */
    class Collapsed(private val peekHeightPx: Float) : CustomBottomSheetValue() {
        override fun calculate(fullHeight: Float, bottomSheetHeight: Float): Float {
            return (fullHeight - peekHeightPx)
        }
    }

    /**
     * The bottom sheet is visible at **halfCoefficient** size of maximum height
     */
    class Half(private val halfCoefficient: Float) : CustomBottomSheetValue() {
        override fun calculate(fullHeight: Float, bottomSheetHeight: Float): Float {
            return (fullHeight - bottomSheetHeight * halfCoefficient)
        }
    }

    /**
     * The bottom sheet is visible at its maximum height.
     */
    object Expanded : CustomBottomSheetValue() {
        override fun calculate(fullHeight: Float, bottomSheetHeight: Float): Float {
            return (fullHeight - bottomSheetHeight)
        }
    }

    /**
     * The bottom sheet is not visible
     */
    object Hidden : CustomBottomSheetValue() {
        override fun calculate(fullHeight: Float, bottomSheetHeight: Float): Float {
            return fullHeight
        }
    }
}
