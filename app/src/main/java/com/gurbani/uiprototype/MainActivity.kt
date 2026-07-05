package com.gurbani.uiprototype

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gurbani.uiprototype.ui.components.FloatingBottomBar
import com.gurbani.uiprototype.ui.components.FloatingTopBar
import com.gurbani.uiprototype.ui.components.NavDestination
import com.gurbani.uiprototype.ui.components.VoiceFollowFab
import com.gurbani.uiprototype.ui.theme.GurbaniPrototypeTheme
import com.gurbani.uiprototype.ui.theme.rememberReduceMotion
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

/**
 * Sample verse text — placeholder content only, for testing the floating
 * glass reader chrome in isolation. Not sourced Gurbani text; do not use
 * this as a content reference. See architecture.md for the real data model.
 */
private val sampleLines = List(60) { index -> "Sample verse line ${index + 1} — placeholder reading content for UI testing." }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GurbaniPrototypeTheme {
                ReaderPrototypeScreen()
            }
        }
    }
}

@Composable
private fun ReaderPrototypeScreen() {
    val hazeState = remember { HazeState() }
    val reduceMotion = rememberReduceMotion()

    var selectedDestination by remember { mutableStateOf(NavDestination.Read) }
    var voiceActive by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Content layer: edge-to-edge, this is what gets blurred behind the
            // floating chrome (design-spec.md: edge-to-edge content, floating
            // inset chrome — two distinct layers, not a contradiction).
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(state = hazeState),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    top = 96.dp,
                    bottom = 112.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sampleLines) { line ->
                    Text(text = line, style = MaterialTheme.typography.bodyLarge)
                }
            }

            // Chrome layer: floats above content with margin, backed by blur.
            FloatingTopBar(
                hazeState = hazeState,
                title = selectedDestination.name,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            VoiceFollowFab(
                isActive = voiceActive,
                onToggle = { voiceActive = !voiceActive },
                reduceMotion = reduceMotion,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 88.dp)
            )

            FloatingBottomBar(
                hazeState = hazeState,
                selected = selectedDestination,
                onSelect = { selectedDestination = it },
                reduceMotion = reduceMotion,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
