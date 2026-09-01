package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CipherMode
import com.example.ui.components.TacticalCard
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.NeonGreenGlow
import com.example.ui.theme.TextTerminalDim
import com.example.ui.theme.TextTerminalPrimary
import com.example.ui.theme.TextTerminalSecondary
import com.example.ui.theme.WarningAmber
import com.example.viewmodel.PrometheusUiState

@Composable
fun CipherCommsScreen(
    state: PrometheusUiState,
    onInputChange: (String) -> Unit,
    onModeChange: (CipherMode) -> Unit,
    onToggleDirection: (Boolean) -> Unit,
    onTransmitToPrometheus: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Mode Selector
        item {
            TacticalCard(title = "ENCRYPTION PROTOCOL ALGORITHM", tag = "0xCIPHER") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        CipherMode.values().forEach { mode ->
                            val isSelected = state.cipherMode == mode
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) NeonGreenGlow else CyberDarkBg)
                                    .border(1.dp, if (isSelected) NeonGreen else CyberBorder, RoundedCornerShape(6.dp))
                                    .clickable { onModeChange(mode) }
                                    .padding(vertical = 8.dp, horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = mode.algorithmTag,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) NeonGreen else TextTerminalDim,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                    Text(
                                        text = mode.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) CyberCyan else TextTerminalSecondary,
                                        fontSize = 8.sp
                                    )
                                }
                            }
                        }
                    }

                    // Direction Selector (Encrypt vs Decrypt)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (state.isEncryptMode) NeonGreen else CyberDarkBg)
                                .border(1.dp, if (state.isEncryptMode) NeonGreen else CyberBorder, RoundedCornerShape(6.dp))
                                .clickable { onToggleDirection(true) }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (state.isEncryptMode) CyberBlack else NeonGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "ENCRYPT (KUNCI PAYLOAD)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (state.isEncryptMode) CyberBlack else NeonGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (!state.isEncryptMode) CyberCyan else CyberDarkBg)
                                .border(1.dp, if (!state.isEncryptMode) CyberCyan else CyberBorder, RoundedCornerShape(6.dp))
                                .clickable { onToggleDirection(false) }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LockOpen,
                                    contentDescription = null,
                                    tint = if (!state.isEncryptMode) CyberBlack else CyberCyan,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "DECRYPT (BONGKAR SANDI)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (!state.isEncryptMode) CyberBlack else CyberCyan,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Input Box
        item {
            TacticalCard(
                title = if (state.isEncryptMode) "PLAINTEXT MESSAGE INPUT" else "ENCRYPTED CIPHERSTREAM INPUT",
                tag = "PAYLOAD-IN"
            ) {
                OutlinedTextField(
                    value = state.cipherInputText,
                    onValueChange = { onInputChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("cipher_input_field"),
                    placeholder = {
                        Text(
                            text = if (state.isEncryptMode) "Masukkan pesan rahasia... (mis: Titik kumpul jam 02.00)" else "Tempelkan payload sandi di sini...",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextTerminalDim,
                            fontSize = 11.sp
                        )
                    },
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        color = if (state.isEncryptMode) NeonGreen else CyberCyan,
                        fontFamily = FontFamily.Monospace
                    ),
                    minLines = 3,
                    maxLines = 5,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonGreen,
                        unfocusedBorderColor = CyberBorder,
                        focusedContainerColor = CyberDarkBg,
                        unfocusedContainerColor = CyberDarkBg
                    )
                )
            }
        }

        // Output Preview
        item {
            TacticalCard(
                title = if (state.isEncryptMode) "ENCRYPTED CIPHER RESULT" else "DECRYPTED PLAINTEXT RESULT",
                tag = "OUTPUT-0x"
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(CyberDarkBg)
                            .border(1.dp, CyberBorder, RoundedCornerShape(6.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = if (state.cipherOutputText.isNotBlank()) state.cipherOutputText else "[STANDBY // NO DATA PROCESSED]",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (state.cipherOutputText.isNotBlank()) WarningAmber else TextTerminalDim,
                            fontFamily = FontFamily.Monospace,
                            lineHeight = 16.sp
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Prometheus Cipher", state.cipherOutputText)
                                clipboard.setPrimaryClip(clip)
                            },
                            enabled = state.cipherOutputText.isNotBlank(),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("copy_cipher_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyberSurfaceVariant,
                                contentColor = NeonGreen
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            Text("SALIN HASIL", style = MaterialTheme.typography.labelSmall)
                        }

                        Button(
                            onClick = {
                                onTransmitToPrometheus("Analisis sandi taktis ini: ${state.cipherOutputText}")
                            },
                            enabled = state.cipherOutputText.isNotBlank(),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("transmit_cipher_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyberCyan,
                                contentColor = CyberBlack
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            Text("KIRIM KE AI", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Quick Dead Drop Generator Samples
        item {
            TacticalCard(title = "DEAD-DROP QUICK TEMPLATES", tag = "OPSEC-PAD") {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(
                        "KODE-01: Rendezvous Flyover Selatan 02.30 WIB",
                        "KODE-02: Paket USB disimpan di kabin loker stasiun #49",
                        "KODE-03: Razia terpantau di jalur bypass, ubah ke rute Charlie"
                    ).forEach { template ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(4.dp))
                                .clickable { onInputChange(template) }
                                .padding(vertical = 4.dp, horizontal = 4.dp),
                            color = CyberSurfaceVariant
                        ) {
                            Text(
                                text = "» $template",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextTerminalSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}
