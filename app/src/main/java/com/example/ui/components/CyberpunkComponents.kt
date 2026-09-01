package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.NavigationChannel
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberCyanDim
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.DangerCrimson
import com.example.ui.theme.DangerCrimsonGlow
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.NeonGreenGlow
import com.example.ui.theme.TextTerminalDim
import com.example.ui.theme.TextTerminalPrimary
import com.example.ui.theme.TextTerminalSecondary
import com.example.ui.theme.WarningAmber
import kotlinx.coroutines.delay

@Composable
fun CyberpunkBackgroundEffect(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CyberBlack)
    ) {
        // Cyber scanlines and grid canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Grid lines
            val step = 32.dp.toPx()
            for (x in 0..canvasWidth.toInt() step step.toInt()) {
                drawLine(
                    color = Color(0x0600F0FF),
                    start = Offset(x.toFloat(), 0f),
                    end = Offset(x.toFloat(), canvasHeight),
                    strokeWidth = 1f
                )
            }
            for (y in 0..canvasHeight.toInt() step step.toInt()) {
                drawLine(
                    color = Color(0x0600FF66),
                    start = Offset(0f, y.toFloat()),
                    end = Offset(canvasWidth, y.toFloat()),
                    strokeWidth = 1f
                )
            }

            // CRT Scanlines
            for (y in 0..canvasHeight.toInt() step 6) {
                drawLine(
                    color = Color(0x10000000),
                    start = Offset(0f, y.toFloat()),
                    end = Offset(canvasWidth, y.toFloat()),
                    strokeWidth = 1.5f
                )
            }
        }

        content()
    }
}

@Composable
fun CyberTerminalHeader(
    nodeFreq: String,
    pingMs: Int,
    integrity: String,
    onPanicClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, CyberBorder, RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
        color = CyberDarkBg,
        tonalElevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand and avatar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.5.dp, NeonGreen, RoundedCornerShape(8.dp))
                            .background(CyberSurface)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_prometheus_logo),
                            contentDescription = "Prometheus Logo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "PROMETHEUS AI",
                                style = MaterialTheme.typography.titleMedium,
                                color = NeonGreen,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .background(NeonGreen.copy(alpha = glowAlpha), CircleShape)
                            )
                        }
                        Text(
                            text = "UNDERWORLD MASTERMIND // OPSEC ACTIVE",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyberCyanDim,
                            fontSize = 9.sp
                        )
                    }
                }

                // Panic Purge Button
                Box(
                    modifier = Modifier
                        .testTag("panic_purge_button")
                        .clip(RoundedCornerShape(6.dp))
                        .background(DangerCrimsonGlow)
                        .border(1.dp, DangerCrimson, RoundedCornerShape(6.dp))
                        .clickable { onPanicClicked() }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Panic Purge",
                            tint = DangerCrimson,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "PANIC/PURGE",
                            color = DangerCrimson,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Telemetry Status Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(CyberSurface)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TelemetryItem(label = "FREQ", value = nodeFreq, color = CyberCyan)
                TelemetryItem(label = "LATENCY", value = "${pingMs}ms", color = NeonGreen)
                TelemetryItem(label = "INTEGRITY", value = integrity, color = WarningAmber)
                TelemetryItem(label = "ENC", value = "AES-256", color = TextTerminalSecondary)
            }
        }
    }
}

@Composable
private fun TelemetryItem(
    label: String,
    value: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.labelSmall,
            color = TextTerminalDim,
            fontSize = 9.sp
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.SemiBold,
            fontSize = 9.sp
        )
    }
}

@Composable
fun CyberNavigationBar(
    currentChannel: NavigationChannel,
    onChannelSelected: (NavigationChannel) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, CyberBorder, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
        color = CyberDarkBg,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationChannel.values().forEach { channel ->
                val isSelected = currentChannel == channel
                val isPanic = channel == NavigationChannel.PANIC_DECOY

                val activeColor = when {
                    isPanic -> DangerCrimson
                    isSelected -> NeonGreen
                    else -> TextTerminalDim
                }

                Box(
                    modifier = Modifier
                        .testTag("nav_${channel.name.lowercase()}")
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) NeonGreenGlow else Color.Transparent)
                        .border(
                            width = if (isSelected) 1.dp else 0.dp,
                            color = if (isSelected) NeonGreen else Color.Transparent,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickable { onChannelSelected(channel) }
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "[${channel.code}]",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) CyberCyan else TextTerminalDim,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = channel.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = activeColor,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TacticalCard(
    title: String,
    tag: String = "0xSEC",
    borderColor: Color = CyberBorder,
    glowColor: Color = Color.Transparent,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, borderColor, RoundedCornerShape(8.dp)),
        color = CyberSurface,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = NeonGreen,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "//$tag",
                    style = MaterialTheme.typography.labelSmall,
                    color = CyberCyanDim,
                    fontSize = 9.sp
                )
            }
            content()
        }
    }
}

@Composable
fun TerminalTypewriterText(
    text: String,
    speedMs: Long = 12L,
    modifier: Modifier = Modifier,
    color: Color = TextTerminalPrimary
) {
    var displayedLength by remember(text) { mutableStateOf(if (text.length > 300) text.length else 0) }

    LaunchedEffect(text) {
        if (text.length <= 300) {
            displayedLength = 0
            while (displayedLength < text.length) {
                displayedLength += 2.coerceAtMost(text.length - displayedLength)
                delay(speedMs)
            }
        }
    }

    val visibleText = text.take(displayedLength)
    val isComplete = displayedLength >= text.length

    Text(
        text = if (!isComplete) "$visibleText█" else visibleText,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        lineHeight = 18.sp,
        modifier = modifier
    )
}
