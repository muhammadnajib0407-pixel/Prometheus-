package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val PrometheusCyberpunkColorScheme = darkColorScheme(
    primary = NeonGreen,
    onPrimary = CyberBlack,
    primaryContainer = CyberSurfaceVariant,
    onPrimaryContainer = NeonGreen,
    
    secondary = CyberCyan,
    onSecondary = CyberBlack,
    secondaryContainer = CyberSurface,
    onSecondaryContainer = CyberCyan,
    
    tertiary = WarningAmber,
    onTertiary = CyberBlack,
    tertiaryContainer = CyberSurfaceVariant,
    onTertiaryContainer = WarningAmber,
    
    background = CyberBlack,
    onBackground = TextTerminalPrimary,
    
    surface = CyberDarkBg,
    onSurface = TextTerminalPrimary,
    surfaceVariant = CyberSurface,
    onSurfaceVariant = TextTerminalSecondary,
    
    error = DangerCrimson,
    onError = CyberBlack,
    errorContainer = CyberSurfaceVariant,
    onErrorContainer = DangerCrimson,
    
    outline = CyberBorder,
    outlineVariant = TextTerminalDim
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PrometheusCyberpunkColorScheme,
        typography = Typography,
        content = content
    )
}

