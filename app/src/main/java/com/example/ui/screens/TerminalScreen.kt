package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TacticalPlaybook
import com.example.model.ChatMessage
import com.example.model.SenderType
import com.example.model.ThreatLevel
import com.example.ui.components.TerminalTypewriterText
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberCyanDim
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.DangerCrimson
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.NeonGreenGlow
import com.example.ui.theme.TextTerminalDim
import com.example.ui.theme.TextTerminalPrimary
import com.example.ui.theme.TextTerminalSecondary
import com.example.ui.theme.WarningAmber
import com.example.viewmodel.PrometheusUiState

@Composable
fun TerminalScreen(
    state: PrometheusUiState,
    onSendMessage: (String) -> Unit,
    onQuickPromptSelected: (String) -> Unit,
    onClearLogs: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(state.messages.size, state.isGenerating) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        // Quick Tactical Directives Scroll Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TacticalPlaybook.QUICK_PROMPTS.forEach { prompt ->
                Box(
                    modifier = Modifier
                        .testTag("quick_prompt_chip")
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyberSurfaceVariant)
                        .border(1.dp, CyberBorder, RoundedCornerShape(6.dp))
                        .clickable {
                            onQuickPromptSelected(prompt)
                        }
                        .padding(horizontal = 9.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = prompt,
                        style = MaterialTheme.typography.labelSmall,
                        color = CyberCyan,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Terminal Chat Stream
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(CyberDarkBg.copy(alpha = 0.85f))
                .border(1.dp, CyberBorder, RoundedCornerShape(8.dp))
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.messages, key = { it.id }) { message ->
                TerminalMessageCard(
                    message = message,
                    onCopy = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Prometheus Tactical Data", message.text)
                        clipboard.setPrimaryClip(clip)
                    }
                )
            }

            if (state.isGenerating) {
                item {
                    GeneratingTerminalGlitch()
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Command Input Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .weight(1f)
                    .testTag("terminal_input_field"),
                placeholder = {
                    Text(
                        text = "Ketik perintah taktis... (mis: rute lolos polisi)",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTerminalDim,
                        fontSize = 12.sp
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = NeonGreen,
                    fontFamily = FontFamily.Monospace
                ),
                maxLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen,
                    unfocusedBorderColor = CyberBorder,
                    focusedContainerColor = CyberSurface,
                    unfocusedContainerColor = CyberDarkBg,
                    cursorColor = NeonGreen
                ),
                shape = RoundedCornerShape(8.dp)
            )

            // Send Button
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .testTag("send_command_button")
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (inputText.isNotBlank() && !state.isGenerating) NeonGreen else CyberSurface)
                    .border(1.dp, if (inputText.isNotBlank()) NeonGreen else CyberBorder, RoundedCornerShape(8.dp))
                    .clickable(enabled = inputText.isNotBlank() && !state.isGenerating) {
                        onSendMessage(inputText)
                        inputText = ""
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send Command",
                    tint = if (inputText.isNotBlank() && !state.isGenerating) CyberBlack else TextTerminalDim,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun TerminalMessageCard(
    message: ChatMessage,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPrometheus = message.sender == SenderType.PROMETHEUS
    val isSystem = message.sender == SenderType.SYSTEM_ALERT

    val cardBorderColor = when {
        isSystem -> WarningAmber
        isPrometheus -> CyberBorder
        else -> CyberCyan.copy(alpha = 0.6f)
    }

    val cardBg = when {
        isSystem -> CyberSurfaceVariant
        isPrometheus -> CyberSurface
        else -> CyberDarkBg
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, cardBorderColor, RoundedCornerShape(6.dp)),
        color = cardBg
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = when (message.sender) {
                            SenderType.PROMETHEUS -> "[PROMETHEUS AI]"
                            SenderType.OPERATOR -> "[OPERATOR]"
                            SenderType.SYSTEM_ALERT -> "[SYSTEM NOTICE]"
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = when (message.sender) {
                            SenderType.PROMETHEUS -> NeonGreen
                            SenderType.OPERATOR -> CyberCyan
                            SenderType.SYSTEM_ALERT -> WarningAmber
                        },
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "TAG: ${message.cipherTag}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextTerminalDim,
                        fontSize = 9.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = message.timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextTerminalSecondary,
                        fontSize = 9.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy message",
                        tint = TextTerminalDim,
                        modifier = Modifier
                            .size(14.dp)
                            .clickable { onCopy() }
                    )
                }
            }

            // Threat Badge if applicable
            if (isPrometheus && message.threatLevel != ThreatLevel.LOW) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color(message.threatLevel.colorHex).copy(alpha = 0.2f))
                        .border(0.5.dp, Color(message.threatLevel.colorHex), RoundedCornerShape(3.dp))
                        .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = message.threatLevel.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(message.threatLevel.colorHex),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Body text
            if (isPrometheus) {
                TerminalTypewriterText(
                    text = message.text,
                    color = TextTerminalPrimary
                )
            } else {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSystem) WarningAmber else CyberCyan,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun GeneratingTerminalGlitch() {
    val infiniteTransition = rememberInfiniteTransition(label = "glitch")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, NeonGreen.copy(alpha = alpha), RoundedCornerShape(6.dp)),
        color = CyberSurface
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = NeonGreen,
                strokeWidth = 2.dp
            )
            Text(
                text = "PROMETHEUS MENGKALKULASI TAKTIK DAN RUTE... █",
                style = MaterialTheme.typography.bodySmall,
                color = NeonGreen,
                fontSize = 11.sp
            )
        }
    }
}
