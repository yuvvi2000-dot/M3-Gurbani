package com.gurbani.uiprototype.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gurbani.uiprototype.ui.theme.MotionDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.HazeMaterials

enum class NavDestination(val label: String, val icon: ImageVector) {
    Read("Read", Icons.Filled.MenuBook),
    Search("Search", Icons.Filled.Search),
    Bookmarks("Saved", Icons.Filled.Bookmark),
    Settings("Settings", Icons.Filled.Settings)
}

/**
 * design-spec.md "Floating chrome specification > Bottom navigation bar":
 * full pill, inset from edges (never flush), short height, own elevation,
 * blurred tonal backdrop, spring-based selection indicator with haptic tick.
 */
@Composable
fun FloatingBottomBar(
    hazeState: HazeState,
    selected: NavDestination,
    onSelect: (NavDestination) -> Unit,
    reduceMotion: Boolean,
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current
    val density = LocalDensity.current

    // x-offset (Dp) of each nav item, measured after layout, so the selection
    // indicator can animate to the selected item's position with a spring.
    var itemPositions by remember { mutableStateOf(mapOf<NavDestination, Dp>()) }

    val indicatorOffset by animateDpAsState(
        targetValue = itemPositions[selected] ?: 0.dp,
        animationSpec = MotionDefaults.navBarIndicatorSpring(reduceMotion),
        label = "navIndicatorOffset"
    )

    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(50))
            .hazeEffect(
                state = hazeState,
                style = HazeMaterials.thin(MaterialTheme.colorScheme.surfaceContainerHigh)
            )
    ) {
        // Selection indicator pill, drawn beneath the icon row.
        Box(
            modifier = Modifier
                .padding(6.dp)
                .size(44.dp)
                .offset(x = indicatorOffset)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f), CircleShape)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavDestination.entries.forEach { destination ->
                val interactionSource = remember { MutableInteractionSource() }
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            val xDp = with(density) { coordinates.positionInParent().x.toDp() }
                            itemPositions = itemPositions + (destination to xDp)
                        }
                        .padding(8.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                            onSelect(destination)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val tint = if (destination == selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                    Icon(imageVector = destination.icon, contentDescription = destination.label, tint = tint)
                }
            }
        }
    }
}
