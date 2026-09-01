package com.example.model

enum class SenderType {
    PROMETHEUS,
    OPERATOR,
    SYSTEM_ALERT
}

enum class ThreatLevel(val label: String, val colorHex: Long) {
    LOW("LOW THREAT // STABLE", 0xFF00FF66),
    ELEVATED("ELEVATED // WATCH SURROUNDINGS", 0xFFFFB300),
    HIGH("HIGH RISK // ACTIVE POLICE / CCTV", 0xFFFF0055),
    CRITICAL("CRITICAL 0x99 // EVASION MANDATORY", 0xFFFF0055)
}

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: SenderType,
    val text: String,
    val timestamp: String,
    val threatLevel: ThreatLevel = ThreatLevel.LOW,
    val isEncrypted: Boolean = false,
    val cipherTag: String = "AES256-GCM"
)

data class OperationPlan(
    val id: String,
    val codeName: String,
    val targetType: String,
    val riskPercentage: Int,
    val reconIntel: String,
    val entryVector: String,
    val countermeasures: String,
    val extractionRoute: String,
    val emergencyContingency: String,
    val gearChecklist: List<String>
)

data class ScannerSignal(
    val frequency: String,
    val channelLabel: String,
    val locationSector: String,
    val signalStrength: Int, // 1 to 5
    val status: String,
    val lastTransmission: String,
    val isAlert: Boolean = false
)

enum class CipherMode(val label: String, val algorithmTag: String) {
    MATRIX_HEX("HEX DUMP", "0xHEX"),
    CYBER_ROT("ROT-13 SHIFT", "ROT-13"),
    GHOST_BASE64("BASE-64 PAYLOAD", "B64-RAW"),
    BINARY_PULSE("BINARY FLUX", "BIN-8BIT")
}

enum class NavigationChannel(val label: String, val code: String, val description: String) {
    TERMINAL("NEURAL TERM", "0x01", "Direct Mastermind Tactical Channel"),
    BLUEPRINT("OPS HEIST", "0x02", "Covert Operation & Blueprint Matrix"),
    CIPHER("CIPHER COMMS", "0x03", "Encrypted Decoder & Dead-Drop Payloads"),
    SCANNER("FREQ RADAR", "0x04", "Tactical Radio Scanner & Audio Intercept"),
    PANIC_DECOY("BURNER PURGE", "0x05", "Emergency Decoy Screen Mode")
}
