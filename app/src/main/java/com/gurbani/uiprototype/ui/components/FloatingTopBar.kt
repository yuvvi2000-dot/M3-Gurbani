package com.gurbani.uiprototype.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.HazeMaterials

/**
 * design-spec.md "Floating chrome specification > Top bar": compact floating
 * capsule, doesn't span full width when content doesn't need it.
 */
@Composable
fun FloatingTopBar(
    hazeState: HazeState,
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(50))
                .hazeEffect(
                    state = hazeState,
                    style = HazeMaterials.thin(MaterialTheme.colorScheme.surfaceContainerHigh)
                )
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
    }
}
