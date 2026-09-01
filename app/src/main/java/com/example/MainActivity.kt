package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.NavigationChannel
import com.example.ui.components.CyberTerminalHeader
import com.example.ui.components.CyberNavigationBar
import com.example.ui.components.CyberpunkBackgroundEffect
import com.example.ui.screens.BlueprintScreen
import com.example.ui.screens.CipherCommsScreen
import com.example.ui.screens.DecoyCalculatorScreen
import com.example.ui.screens.RadioScannerScreen
import com.example.ui.screens.TerminalScreen
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.PrometheusViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                PrometheusApp()
            }
        }
    }
}

@Composable
fun PrometheusApp(
    viewModel: PrometheusViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearToast()
        }
    }

    if (uiState.isDecoyActive) {
        // Fullscreen Decoy Calculator Mode
        DecoyCalculatorScreen(
            state = uiState,
            onButtonClick = { viewModel.handleDecoyInput(it) },
            onSecretExit = { viewModel.exitDecoyMode() },
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        )
    } else {
        // Full Cyberpunk Terminal UI
        CyberpunkBackgroundEffect {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = CyberBlack,
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = {
                    CyberTerminalHeader(
                        nodeFreq = uiState.nodeFrequency,
                        pingMs = uiState.pingMs,
                        integrity = uiState.memoryIntegrity,
                        onPanicClicked = { viewModel.activateDecoyMode() },
                        modifier = Modifier.statusBarsPadding()
                    )
                },
                bottomBar = {
                    CyberNavigationBar(
                        currentChannel = uiState.activeChannel,
                        onChannelSelected = { viewModel.selectChannel(it) },
                        modifier = Modifier.navigationBarsPadding()
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Crossfade(
                        targetState = uiState.activeChannel,
                        label = "channel_transition"
                    ) { channel ->
                        when (channel) {
                            NavigationChannel.TERMINAL -> {
                                TerminalScreen(
                                    state = uiState,
                                    onSendMessage = { viewModel.sendUserMessage(it) },
                                    onQuickPromptSelected = { viewModel.sendUserMessage(it) },
                                    onClearLogs = { viewModel.clearChatLogs() }
                                )
                            }
                            NavigationChannel.BLUEPRINT -> {
                                BlueprintScreen(
                                    state = uiState,
                                    onSelectOperation = { viewModel.selectOperation(it) },
                                    onGenerateCustom = { target, risk ->
                                        viewModel.generateCustomOperation(target, risk)
                                    },
                                    onCustomTargetChange = { viewModel.updateCustomTargetInput(it) },
                                    onCustomRiskChange = { viewModel.updateCustomRiskSlider(it) }
                                )
                            }
                            NavigationChannel.CIPHER -> {
                                CipherCommsScreen(
                                    state = uiState,
                                    onInputChange = { viewModel.updateCipherInput(it) },
                                    onModeChange = { viewModel.setCipherMode(it) },
                                    onToggleDirection = { viewModel.toggleCipherDirection(it) },
                                    onTransmitToPrometheus = {
                                        viewModel.selectChannel(NavigationChannel.TERMINAL)
                                        viewModel.sendUserMessage(it)
                                    }
                                )
                            }
                            NavigationChannel.SCANNER -> {
                                RadioScannerScreen(
                                    state = uiState,
                                    onTuneFrequency = { viewModel.tuneFrequency(it) },
                                    onToggleJammer = { viewModel.toggleJammer() },
                                    onAskAiAboutFrequency = {
                                        viewModel.selectChannel(NavigationChannel.TERMINAL)
                                        viewModel.sendUserMessage(it)
                                    }
                                )
                            }
                            NavigationChannel.PANIC_DECOY -> {
                                // Handled by outer condition
                            }
                        }
                    }
                }
            }
        }
    }
}
