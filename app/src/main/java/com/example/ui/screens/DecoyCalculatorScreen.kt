package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.PrometheusUiState

@Composable
fun DecoyCalculatorScreen(
    state: PrometheusUiState,
    onButtonClick: (String) -> Unit,
    onSecretExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttons = listOf(
        listOf("C", "±", "%", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "=")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF171717))
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Discreet decoy header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Calculator",
                color = Color(0xFF6B7280),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            // Discrete secret unlock toggle (for user convenience)
            Text(
                text = "[PIN: 7777 = ]",
                color = Color(0xFF374151),
                fontSize = 10.sp,
                modifier = Modifier.clickable { onSecretExit() }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Calculation Expression & Result Display
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.End
        ) {
            if (state.decoyExpression.isNotBlank()) {
                Text(
                    text = state.decoyExpression,
                    color = Color(0xFF9CA3AF),
                    fontSize = 20.sp,
                    textAlign = TextAlign.End
                )
            }
            Text(
                text = state.decoyDisplay,
                color = Color.White,
                fontSize = 54.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier.testTag("calculator_display")
            )
        }

        // Calculator Buttons Grid
        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                row.forEach { btn ->
                    val isZero = btn == "0"
                    val isOperator = btn in listOf("÷", "×", "-", "+", "=")
                    val isTopAction = btn in listOf("C", "±", "%")

                    val btnBg = when {
                        isOperator -> Color(0xFFFF9F0A)
                        isTopAction -> Color(0xFFA5A5A5)
                        else -> Color(0xFF333333)
                    }

                    val btnTextColor = when {
                        isTopAction -> Color.Black
                        else -> Color.White
                    }

                    Box(
                        modifier = Modifier
                            .weight(if (isZero) 2f else 1f)
                            .aspectRatio(if (isZero) 2.1f else 1f)
                            .clip(CircleShape)
                            .background(btnBg)
                            .clickable {
                                if (btn == "±" || btn == "%") {
                                    // simple no-op / placeholder for visual completeness
                                } else {
                                    onButtonClick(btn)
                                }
                            },
                        contentAlignment = if (isZero) Alignment.CenterStart else Alignment.Center
                    ) {
                        Text(
                            text = btn,
                            color = btnTextColor,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = if (isZero) Modifier.padding(start = 28.dp) else Modifier
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}
