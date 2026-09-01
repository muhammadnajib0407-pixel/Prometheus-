package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CellWifi
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScannerSignal
import com.example.ui.components.TacticalCard
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
import com.example.viewmodel.PrometheusUiState
import kotlin.math.sin

@Composable
fun RadioScannerScreen(
    state: PrometheusUiState,
    onTuneFrequency: (ScannerSignal) -> Unit,
    onToggleJammer: () -> Unit,
    onAskAiAboutFrequency: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "scanner_wave")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Oscilloscope Radio Wave Canvas
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, if (state.isJammerActive) DangerCrimson else CyberBorder, RoundedCornerShape(8.dp)),
                color = CyberDarkBg
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height
                        val midY = height / 2

                        // Draw Grid
                        drawLine(
                            color = Color(0x1500F0FF),
                            start = Offset(0f, midY),
                            end = Offset(width, midY),
                            strokeWidth = 1f
                        )

                        // Draw Waveform
                        val path = Path()
                        path.moveTo(0f, midY)

                        val waveColor = if (state.isJammerActive) DangerCrimson else NeonGreen
                        val frequencyScale = if (state.isJammerActive) 0.08f else 0.035f
                        val amplitude = if (state.isJammerActive) midY * 0.75f else midY * 0.45f

                        for (x in 0..width.toInt() step 4) {
                            val noise = if (state.isJammerActive) ((-10..10).random() * 1.2f) else 0f
                            val y = midY + sin((x * frequencyScale) + phase) * amplitude + noise
                            path.lineTo(x.toFloat(), y)
                        }

                        drawPath(
                            path = path,
                            color = waveColor,
                            style = Stroke(width = 2.5f)
                        )
                    }

                    // Overlay HUD text on canvas
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "SCOPE // FREQ: ${state.activeFrequency}",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (state.isJammerActive) DangerCrimson else CyberCyan,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (state.isJammerActive) "STATUS: RF JAMMING OVERLAY ACTIVE (2.4/5.8 GHz)" else "STATUS: SIGNAL LOCKED // INTERCEPTING",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (state.isJammerActive) DangerCrimson else TextTerminalDim,
                            fontSize = 8.sp
                        )
                    }
                }
            }
        }

        // RF Jammer Tactical Toggle
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (state.isJammerActive) DangerCrimsonGlow else CyberSurface)
                    .border(1.dp, if (state.isJammerActive) DangerCrimson else CyberBorder, RoundedCornerShape(8.dp))
                    .clickable { onToggleJammer() }
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (state.isJammerActive) Icons.Default.Warning else Icons.Default.CellWifi,
                            contentDescription = null,
                            tint = if (state.isJammerActive) DangerCrimson else WarningAmber,
                            modifier = Modifier.size(22.dp)
                        )
                        Column {
                            Text(
                                text = if (state.isJammerActive) "RF SIGNAL JAMMER: [AKTIF]" else "RF SIGNAL JAMMER: [STANDBY]",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (state.isJammerActive) DangerCrimson else TextTerminalPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (state.isJammerActive) "Memancarkan derau sinyal pengacak frekuensi radio & CCTV" else "Ketuk untuk aktifkan pengacak sinyal darurat",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextTerminalSecondary,
                                fontSize = 10.sp
                            )
                        }
                    }

                    Text(
                        text = if (state.isJammerActive) "DISENGAGE" else "ENGAGE",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (state.isJammerActive) DangerCrimson else NeonGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Channels List
        item {
            Text(
                text = "INTERCEPTED FREQUENCY CHANNELS:",
                style = MaterialTheme.typography.labelSmall,
                color = CyberCyan,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
            )
        }

        items(state.scannerSignals) { signal ->
            val isTuned = state.activeFrequency == signal.frequency
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .border(
                        1.dp,
                        if (isTuned) NeonGreen else if (signal.isAlert) DangerCrimson.copy(alpha = 0.6f) else CyberBorder,
                        RoundedCornerShape(6.dp)
                    )
                    .clickable { onTuneFrequency(signal) },
                color = if (isTuned) CyberSurfaceVariant else CyberSurface
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = signal.frequency,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isTuned) NeonGreen else CyberCyan,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = signal.channelLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextTerminalPrimary,
                                fontSize = 11.sp
                            )
                        }

                        // Signal Bars
                        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            for (i in 1..5) {
                                Box(
                                    modifier = Modifier
                                        .size(width = 3.dp, height = (i * 3).dp)
                                        .background(if (i <= signal.signalStrength) NeonGreen else TextTerminalDim)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "SEKTOR: ${signal.locationSector} // ${signal.status}",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (signal.isAlert) WarningAmber else TextTerminalDim,
                        fontSize = 9.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = signal.lastTransmission,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isTuned) TextTerminalPrimary else TextTerminalSecondary,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp
                    )

                    if (isTuned) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                onAskAiAboutFrequency("Analisis transmisi radio polisi ini dan beri saran taktis: \"${signal.lastTransmission}\"")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyberCyan,
                                contentColor = CyberBlack
                            ),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "MINTA TAKTIK PROMETHEUS TENTANG INI",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }

        // Live Log Stream
        if (state.scannerLog.isNotEmpty()) {
            item {
                TacticalCard(title = "FREQUENCY INTERCEPT LOGS", tag = "FIFO-BUFFER") {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        state.scannerLog.forEach { log ->
                            Text(
                                text = log,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextTerminalDim,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}
