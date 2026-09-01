package com.example.ui.screens

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TacticalPlaybook
import com.example.model.OperationPlan
import com.example.ui.components.TacticalCard
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCyan
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
fun BlueprintScreen(
    state: PrometheusUiState,
    onSelectOperation: (OperationPlan) -> Unit,
    onGenerateCustom: (String, Float) -> Unit,
    onCustomTargetChange: (String) -> Unit,
    onCustomRiskChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val checkedGear = remember { mutableStateListOf<String>() }
    val currentOp = state.selectedOperation

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Preset Operation Switcher
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "SELECT OPERATIONAL BLUEPRINT:",
                    style = MaterialTheme.typography.labelSmall,
                    color = CyberCyan,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TacticalPlaybook.PRESET_OPERATIONS.forEach { op ->
                        val isSelected = currentOp.id == op.id
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) NeonGreenGlow else CyberSurface)
                                .border(1.dp, if (isSelected) NeonGreen else CyberBorder, RoundedCornerShape(6.dp))
                                .clickable { onSelectOperation(op) }
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = op.codeName.replace("OPERATION ", ""),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) NeonGreen else TextTerminalDim,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        }
                    }
                }
            }
        }

        // Active Blueprint Header Card
        item {
            TacticalCard(
                title = currentOp.codeName,
                tag = "RISK ${currentOp.riskPercentage}%",
                borderColor = if (currentOp.riskPercentage > 75) DangerCrimson else NeonGreen
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TARGET: ${currentOp.targetType}",
                            style = MaterialTheme.typography.bodySmall,
                            color = WarningAmber,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (currentOp.riskPercentage > 75) "THREAT: HIGH" else "THREAT: MODERATE",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (currentOp.riskPercentage > 75) DangerCrimson else NeonGreen,
                            fontSize = 10.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { currentOp.riskPercentage / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = when {
                            currentOp.riskPercentage > 75 -> DangerCrimson
                            currentOp.riskPercentage > 50 -> WarningAmber
                            else -> NeonGreen
                        },
                        trackColor = CyberDarkBg
                    )
                }
            }
        }

        // Phase 1: Recon & Blindspots
        item {
            PhaseCard(
                icon = Icons.Default.Visibility,
                phaseTitle = "PHASE 1: RECON & CCTV BLINDSPOTS",
                content = currentOp.reconIntel,
                accentColor = CyberCyan
            )
        }

        // Phase 2: Infiltration Vector
        item {
            PhaseCard(
                icon = Icons.Default.LockOpen,
                phaseTitle = "PHASE 2: INFILTRATION & ENTRY VECTOR",
                content = currentOp.entryVector,
                accentColor = NeonGreen
            )
        }

        // Phase 3: Jamming & Electronic Countermeasures
        item {
            PhaseCard(
                icon = Icons.Default.ElectricBolt,
                phaseTitle = "PHASE 3: SIGNAL JAMMING & BYPASS",
                content = currentOp.countermeasures,
                accentColor = WarningAmber
            )
        }

        // Phase 4: Extraction & Getaway
        item {
            PhaseCard(
                icon = Icons.Default.Shield,
                phaseTitle = "PHASE 4: EXTRACTION & GETAWAY ROUTE",
                content = currentOp.extractionRoute,
                accentColor = CyberCyan
            )
        }

        // Phase 5: Contingency Plan B
        item {
            PhaseCard(
                icon = Icons.Default.Warning,
                phaseTitle = "PHASE 5: CONTINGENCY (PLAN B // SCATTER)",
                content = currentOp.emergencyContingency,
                accentColor = DangerCrimson
            )
        }

        // Gear Checklist
        item {
            TacticalCard(title = "TACTICAL EQUIPMENT LOADOUT", tag = "GEAR-REQ") {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    currentOp.gearChecklist.forEach { gear ->
                        val isChecked = checkedGear.contains(gear)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                    if (isChecked) checkedGear.remove(gear) else checkedGear.add(gear)
                                }
                                .padding(vertical = 4.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isChecked) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                contentDescription = "Check gear",
                                tint = if (isChecked) NeonGreen else TextTerminalDim,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = gear,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isChecked) NeonGreen else TextTerminalPrimary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Custom Heist Generator Form
        item {
            TacticalCard(title = "CUSTOM TARGET GENERATOR", tag = "AI-SYNTH") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = state.customTargetInput,
                        onValueChange = { onCustomTargetChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("custom_target_input"),
                        placeholder = {
                            Text(
                                text = "Nama Target / Lokasi (mis: Brankas Bank Sektor 4)",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextTerminalDim,
                                fontSize = 11.sp
                            )
                        },
                        textStyle = MaterialTheme.typography.bodySmall.copy(color = NeonGreen),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonGreen,
                            unfocusedBorderColor = CyberBorder,
                            focusedContainerColor = CyberSurfaceVariant,
                            unfocusedContainerColor = CyberDarkBg
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ESTIMATED RISK FACTOR:",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextTerminalDim
                        )
                        Text(
                            text = "${state.customRiskSlider.toInt()}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (state.customRiskSlider > 75) DangerCrimson else WarningAmber,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Slider(
                        value = state.customRiskSlider,
                        onValueChange = { onCustomRiskChange(it) },
                        valueRange = 20f..95f,
                        colors = SliderDefaults.colors(
                            thumbColor = NeonGreen,
                            activeTrackColor = WarningAmber,
                            inactiveTrackColor = CyberDarkBg
                        )
                    )

                    Button(
                        onClick = {
                            onGenerateCustom(state.customTargetInput, state.customRiskSlider)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("generate_blueprint_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonGreen,
                            contentColor = CyberBlack
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "GENERATE BLUEPRINT TAKTIS",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
private fun PhaseCard(
    icon: ImageVector,
    phaseTitle: String,
    content: String,
    accentColor: Color
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .border(1.dp, CyberBorder, RoundedCornerShape(6.dp)),
        color = CyberSurface
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = phaseTitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = accentColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = TextTerminalPrimary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )
        }
    }
}
