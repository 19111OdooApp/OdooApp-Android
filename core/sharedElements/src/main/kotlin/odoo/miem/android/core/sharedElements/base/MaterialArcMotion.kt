package odoo.miem.android.core.sharedElements.base

import androidx.compose.ui.geometry.Offset
import odoo.miem.android.core.sharedElements.utils.QuadraticBezier

class MaterialArcMotion : KeyframeBasedMotion() {

    override fun getKeyframes(start: Offset, end: Offset): Pair<FloatArray, LongArray> =
        QuadraticBezier.approximate(
            start,
            if (start.y > end.y) Offset(end.x, start.y) else Offset(start.x, end.y),
            end,
            ACCEPTABLE_ERROR
        )

    private companion object {
        const val ACCEPTABLE_ERROR = 0.5f
    }
}

val MaterialArcMotionFactory: PathMotionFactory = { MaterialArcMotion() }
