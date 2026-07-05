package com.gurbani.uiprototype.ui.theme

import android.content.Context
import android.provider.Settings
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp

/**
 * design-spec.md "Motion + haptics": under-damped (visibly bouncy) springs for
 * floating chrome, but everything must collapse to a fast, non-bouncy tween-like
 * spring when the system's reduced-motion / animator-duration-scale setting is 0.
 */
object MotionDefaults {

    fun <T> expressiveSpring(reduceMotion: Boolean): SpringSpec<T> = if (reduceMotion) {
        spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessHigh)
    } else {
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
    }

    fun navBarIndicatorSpring(reduceMotion: Boolean): SpringSpec<Dp> = if (reduceMotion) {
        spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessHigh)
    } else {
        // Slightly bouncier than the generic default per design-spec.md's
        // nav-bar-selection row (visible ~8-12% overshoot).
        spring(dampingRatio = 0.55f, stiffness = Spring.StiffnessMediumLow)
    }
}

/**
 * Reads the system-wide "Remove animations" accessibility setting
 * (ANIMATOR_DURATION_SCALE == 0) so floating chrome can respect it, per
 * design-spec.md's "Respect system reduced-motion" requirement.
 */
@Composable
fun rememberReduceMotion(): Boolean {
    val context = LocalContext.current
    return remember { isAnimatorScaleZero(context) }
}

private fun isAnimatorScaleZero(context: Context): Boolean {
    val scale = Settings.Global.getFloat(
        context.contentResolver,
        Settings.Global.ANIMATOR_DURATION_SCALE,
        1f
    )
    return scale == 0f
}
