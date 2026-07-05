package com.gurbani.uiprototype.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Precedence per theming-spec.md: dynamic color (Android 12+) is used when
 * available and no explicit theme pack is selected. This prototype has no
 * theme-pack selection UI, so it only demonstrates the Dynamic vs. Fallback
 * branch of that resolution logic.
 */
@Composable
fun GurbaniPrototypeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = SaffronPrimaryDark,
            secondary = DeepIndigoSecondaryDark,
            tertiary = TertiaryAccentDark,
            background = PaperDark,
            surface = PaperDark,
            surfaceContainerHigh = PaperContainerDark
        )
        else -> lightColorScheme(
            primary = SaffronPrimary,
            secondary = DeepIndigoSecondary,
            tertiary = TertiaryAccent,
            background = PaperLight,
            surface = PaperLight,
            surfaceContainerHigh = PaperContainerLight
        )
    }

    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        content = content
    )
}
