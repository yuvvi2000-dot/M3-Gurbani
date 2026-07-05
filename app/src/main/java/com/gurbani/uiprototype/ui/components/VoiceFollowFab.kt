package com.gurbani.uiprototype.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.gurbani.uiprototype.ui.theme.MotionDefaults

/**
 * design-spec.md "Shape guidance" hero moment #1: circular at rest, morphs
 * toward a rounded-square "recording" shape on activation, paired with a
 * confirm-strength haptic (not a light tick) since this commits to a session.
 */
@Composable
fun VoiceFollowFab(
    isActive: Boolean,
    onToggle: () -> Unit,
    reduceMotion: Boolean,
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }

    val cornerRadius by animateDpAsState(
        targetValue = if (isActive) 20.dp else 28.dp,
        animationSpec = MotionDefaults.expressiveSpring(reduceMotion),
        label = "fabCornerMorph"
    )

    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .size(56.dp)
            .background(
                color = if (isActive) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(cornerRadius)
            )
            .clickable(interactionSource = interactionSource, indication = null) {
                haptics.performHapticFeedback(HapticFeedbackType.Confirm)
                onToggle()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isActive) Icons.Filled.Stop else Icons.Filled.Mic,
            contentDescription = if (isActive) "Stop voice-follow" else "Start voice-follow",
            tint = if (isActive) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
