package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GeminiRepository
import com.example.data.TacticalPlaybook
import com.example.model.ChatMessage
import com.example.model.CipherMode
import com.example.model.NavigationChannel
import com.example.model.OperationPlan
import com.example.model.ScannerSignal
import com.example.model.SenderType
import com.example.model.ThreatLevel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

data class PrometheusUiState(
    val activeChannel: NavigationChannel = NavigationChannel.TERMINAL,
    val messages: List<ChatMessage> = emptyList(),
    val isGenerating: Boolean = false,
    val streamingText: String = "",
    val nodeFrequency: String = "433.920 MHz",
    val encryptionKeyHash: String = "0x9F4A...AES256",
    val pingMs: Int = 14,
    val memoryIntegrity: String = "99.8%",
    
    // Heist Blueprint State
    val selectedOperation: OperationPlan = TacticalPlaybook.PRESET_OPERATIONS.first(),
    val customTargetInput: String = "",
    val customRiskSlider: Float = 65f,
    
    // Cipher State
    val cipherInputText: String = "",
    val cipherOutputText: String = "",
    val cipherMode: CipherMode = CipherMode.MATRIX_HEX,
    val isEncryptMode: Boolean = true,
    
    // Scanner State
    val scannerSignals: List<ScannerSignal> = TacticalPlaybook.SCANNER_FEEDS,
    val activeFrequency: String = "154.650 MHz",
    val isJammerActive: Boolean = false,
    val scannerLog: List<String> = emptyList(),
    
    // Decoy Calculator State
    val isDecoyActive: Boolean = false,
    val decoyDisplay: String = "0",
    val decoyExpression: String = "",
    val toastMessage: String? = null
)

class PrometheusViewModel(
    private val repository: GeminiRepository = GeminiRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrometheusUiState())
    val uiState: StateFlow<PrometheusUiState> = _uiState.asStateFlow()

    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    init {
        // Initial underworld handshake message
        val welcomeMsg = ChatMessage(
            id = "init_01",
            sender = SenderType.PROMETHEUS,
            text = """
[PROMETHEUS NEURAL LINK ESTABLISHED]
[NODE FREQ: 433.920 MHz // STATUS: ANONYMOUS]

Dengar sini, operator. Gua Prometheus AI. Di jaringan gelap ini, gak ada ruang buat amatir.

Kalau lo butuh strategi infiltrasi, rute lolos dari razia polisi, pengalihan CCTV, atau rencana heist bersih—sebutkan situasinya. Gua yang pegang kalkulasi taktis lo.

Ketik perintah lo atau pilih modul protokol di bawah. Ingat: main dingin, patuhi OPSEC.
            """.trimIndent(),
            timestamp = getCurrentTime(),
            threatLevel = ThreatLevel.LOW,
            cipherTag = "RSA-4096"
        )
        _uiState.update { it.copy(messages = listOf(welcomeMsg)) }

        // Start subtle background telemetry fluctuation
        startTelemetrySimulation()
    }

    private fun getCurrentTime(): String = timeFormat.format(Date())

    private fun startTelemetrySimulation() {
        viewModelScope.launch {
            while (true) {
                delay(3500)
                val jitter = (-15..15).random()
                val baseFreq = 433.920 + (jitter / 1000.0)
                val newPing = (12..28).random()
                val integrity = if ((1..10).random() == 1) "98.7%" else "99.9%"
                _uiState.update {
                    it.copy(
                        nodeFrequency = String.format(Locale.US, "%.3f MHz", baseFreq),
                        pingMs = newPing,
                        memoryIntegrity = integrity
                    )
                }
            }
        }
    }

    fun selectChannel(channel: NavigationChannel) {
        if (channel == NavigationChannel.PANIC_DECOY) {
            activateDecoyMode()
        } else {
            _uiState.update { it.copy(activeChannel = channel) }
        }
    }

    fun sendUserMessage(rawText: String) {
        val text = rawText.trim()
        if (text.isBlank()) return

        val userMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = SenderType.OPERATOR,
            text = text,
            timestamp = getCurrentTime(),
            threatLevel = detectThreatLevel(text),
            cipherTag = "AES-GCM"
        )

        _uiState.update {
            it.copy(
                messages = it.messages + userMessage,
                isGenerating = true
            )
        }

        viewModelScope.launch {
            val history = _uiState.value.messages.map {
                (if (it.sender == SenderType.OPERATOR) "user" else "model") to it.text
            }

            val responseText = repository.getTacticalAdvice(text, history)

            val aiMessage = ChatMessage(
                id = UUID.randomUUID().toString(),
                sender = SenderType.PROMETHEUS,
                text = responseText,
                timestamp = getCurrentTime(),
                threatLevel = detectThreatLevel(responseText),
                cipherTag = "0xPROMETHEUS"
            )

            _uiState.update {
                it.copy(
                    messages = it.messages + aiMessage,
                    isGenerating = false
                )
            }
        }
    }

    private fun detectThreatLevel(text: String): ThreatLevel {
        val lower = text.lowercase()
        return when {
            lower.contains("razia") || lower.contains("polisi") || lower.contains("kepung") || lower.contains("sirine") -> ThreatLevel.HIGH
            lower.contains("buntuti") || lower.contains("cctv") || lower.contains("bobol") || lower.contains("heist") -> ThreatLevel.ELEVATED
            lower.contains("darurat") || lower.contains("gagal") -> ThreatLevel.CRITICAL
            else -> ThreatLevel.LOW
        }
    }

    fun selectOperation(op: OperationPlan) {
        _uiState.update { it.copy(selectedOperation = op) }
    }

    fun generateCustomOperation(target: String, risk: Float) {
        val targetName = if (target.isNotBlank()) target else "Fasilitas Perbankan / Brankas Data Sektor 7"
        val calculatedRisk = risk.toInt().coerceIn(20, 99)

        val newOp = OperationPlan(
            id = "op_${System.currentTimeMillis()}",
            codeName = "OPERATION BLACKOUT-${(100..999).random()}",
            targetType = targetName,
            riskPercentage = calculatedRisk,
            reconIntel = "Target: $targetName. Patroli rutin per 30 menit. Terdapat 4 kamera fixed dan sensor gerak infra-merah di gerbang belakang.",
            entryVector = "Infiltrasi titik buta utilitas barat daya jam 02.40 WIB. Potong rantai pagar sekunder dengan bolt cutter mini.",
            countermeasures = "Gunakan EMP pulse / radio frequency jammer 433MHz untuk menonaktifkan trigger alarm selama 120 detik.",
            extractionRoute = "Jalur selokan bawah tanah -> tembus ke jalan tikus perumahan lama -> rendezvous dengan van hitam plat ganti.",
            emergencyContingency = "Jika alarm aktif: Buang alat berat, sebar kelereng & paku payung di lorong masuk, lolos lewat ventilasi darurat.",
            gearChecklist = listOf(
                "Portable RF Jammer",
                "Thermal Evasion Suit",
                "Lockpick & Tension Wrench",
                "Burner Device Clean",
                "Smoke Dispersal Canister"
            )
        )

        _uiState.update {
            it.copy(
                selectedOperation = newOp,
                customTargetInput = "",
                toastMessage = "BLUEPRINT PROTOCOL COMPILED"
            )
        }
    }

    fun updateCustomTargetInput(input: String) {
        _uiState.update { it.copy(customTargetInput = input) }
    }

    fun updateCustomRiskSlider(value: Float) {
        _uiState.update { it.copy(customRiskSlider = value) }
    }

    fun updateCipherInput(text: String) {
        val output = if (_uiState.value.isEncryptMode) {
            TacticalPlaybook.encryptText(text, _uiState.value.cipherMode)
        } else {
            TacticalPlaybook.decryptText(text, _uiState.value.cipherMode)
        }
        _uiState.update {
            it.copy(
                cipherInputText = text,
                cipherOutputText = output
            )
        }
    }

    fun setCipherMode(mode: CipherMode) {
        val output = if (_uiState.value.isEncryptMode) {
            TacticalPlaybook.encryptText(_uiState.value.cipherInputText, mode)
        } else {
            TacticalPlaybook.decryptText(_uiState.value.cipherInputText, mode)
        }
        _uiState.update {
            it.copy(
                cipherMode = mode,
                cipherOutputText = output
            )
        }
    }

    fun toggleCipherDirection(isEncrypt: Boolean) {
        val output = if (isEncrypt) {
            TacticalPlaybook.encryptText(_uiState.value.cipherInputText, _uiState.value.cipherMode)
        } else {
            TacticalPlaybook.decryptText(_uiState.value.cipherInputText, _uiState.value.cipherMode)
        }
        _uiState.update {
            it.copy(
                isEncryptMode = isEncrypt,
                cipherOutputText = output
            )
        }
    }

    fun tuneFrequency(signal: ScannerSignal) {
        _uiState.update {
            it.copy(
                activeFrequency = signal.frequency,
                scannerLog = listOf(
                    "[${getCurrentTime()}] TUNED TO ${signal.frequency} - ${signal.channelLabel}",
                    "[${getCurrentTime()}] ${signal.lastTransmission}"
                ) + it.scannerLog.take(10)
            )
        }
    }

    fun toggleJammer() {
        val newState = !_uiState.value.isJammerActive
        _uiState.update {
            it.copy(
                isJammerActive = newState,
                scannerLog = listOf(
                    "[${getCurrentTime()}] " + (if (newState) "⚠️ RF JAMMER BROADCASTING 2.4/5.8GHz STATIC BLANKET" else "RF JAMMER STANDBY")
                ) + it.scannerLog.take(10)
            )
        }
    }

    fun clearChatLogs() {
        val purgeNotice = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = SenderType.SYSTEM_ALERT,
            text = "[TACTICAL PURGE COMPLETED // ZERO TRACES REMAINING]",
            timestamp = getCurrentTime(),
            threatLevel = ThreatLevel.LOW,
            cipherTag = "WIPED"
        )
        _uiState.update {
            it.copy(
                messages = listOf(purgeNotice),
                toastMessage = "LOGS PURGED"
            )
        }
    }

    // Decoy Calculator Logic
    fun activateDecoyMode() {
        _uiState.update {
            it.copy(
                isDecoyActive = true,
                decoyDisplay = "0",
                decoyExpression = ""
            )
        }
    }

    fun exitDecoyMode() {
        _uiState.update {
            it.copy(
                isDecoyActive = false,
                activeChannel = NavigationChannel.TERMINAL,
                toastMessage = "PROMETHEUS NEURAL LINK RESTORED"
            )
        }
    }

    fun handleDecoyInput(btn: String) {
        when (btn) {
            "C" -> _uiState.update { it.copy(decoyDisplay = "0", decoyExpression = "") }
            "=" -> {
                // Check if user entered secret code 7777
                if (_uiState.value.decoyDisplay == "7777" || _uiState.value.decoyExpression == "7777") {
                    exitDecoyMode()
                    return
                }
                // Calculate simple arithmetic
                try {
                    val exp = _uiState.value.decoyExpression + _uiState.value.decoyDisplay
                    val result = evaluateSimpleExpression(exp)
                    _uiState.update {
                        it.copy(
                            decoyDisplay = result,
                            decoyExpression = ""
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(decoyDisplay = "Error") }
                }
            }
            "+", "-", "×", "÷" -> {
                val current = _uiState.value.decoyDisplay
                _uiState.update {
                    it.copy(
                        decoyExpression = "$current $btn ",
                        decoyDisplay = "0"
                    )
                }
            }
            else -> {
                val current = _uiState.value.decoyDisplay
                val newDisplay = if (current == "0" && btn != ".") btn else current + btn
                _uiState.update { it.copy(decoyDisplay = newDisplay) }
            }
        }
    }

    private fun evaluateSimpleExpression(expr: String): String {
        val parts = expr.split(" ").filter { it.isNotBlank() }
        if (parts.size < 3) return parts.firstOrNull() ?: "0"
        val a = parts[0].toDoubleOrNull() ?: 0.0
        val op = parts[1]
        val b = parts[2].toDoubleOrNull() ?: 0.0
        val res = when (op) {
            "+" -> a + b
            "-" -> a - b
            "×", "*" -> a * b
            "÷", "/" -> if (b != 0.0) a / b else 0.0
            else -> a
        }
        return if (res % 1.0 == 0.0) res.toLong().toString() else "%.4f".format(Locale.US, res)
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
