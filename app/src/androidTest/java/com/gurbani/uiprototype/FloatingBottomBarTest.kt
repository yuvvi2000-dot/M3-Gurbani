package com.gurbani.uiprototype

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.gurbani.uiprototype.ui.components.FloatingBottomBar
import com.gurbani.uiprototype.ui.components.NavDestination
import dev.chrisbanes.haze.HazeState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Sanity test for the floating nav bar prototype: verifies each destination
 * renders and that tapping an item updates selection state. This does not
 * assert on blur/motion visuals (not practical in a Compose UI test) — it's
 * a smoke test that the component composes and responds to input, meant to
 * be run alongside manually eyeballing the app on-device/emulator.
 */
class FloatingBottomBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun tappingDestination_updatesSelection() {
        var selected: NavDestination = NavDestination.Read

        composeTestRule.setContent {
            MaterialTheme {
                var current by remember { mutableStateOf(NavDestination.Read) }
                FloatingBottomBar(
                    hazeState = remember { HazeState() },
                    selected = current,
                    onSelect = {
                        current = it
                        selected = it
                    },
                    reduceMotion = true
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(NavDestination.Search.label).performClick()

        assertEquals(NavDestination.Search, selected)
    }
}
