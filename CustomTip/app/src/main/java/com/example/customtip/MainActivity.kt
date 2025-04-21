package com.example.customtip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customtip.ui.theme.CustomTipTheme
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomTipTheme {
                TipScreen(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxSize()
                )
            }
        }
    }
}

fun getTip(billAmount: String, tipPercentage: Double, roundUp: Boolean): Double {
    if (roundUp) {
        return ceil(billAmount.toDoubleOrNull()?.times(tipPercentage) ?: 0.0)
    }
    return billAmount.toDoubleOrNull()?.times(0.15) ?: 0.0
}

@Composable
fun TipScreen(modifier: Modifier = Modifier) {
    var billAmount by remember { mutableStateOf("") }
    var tip by remember { mutableDoubleStateOf(0.0) }
    var checked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(height = 150.dp))
        Text("Calculate Tip")
        Spacer(modifier = Modifier.height(height = 20.dp))
        TextField(
            value = billAmount,
            onValueChange = {
                billAmount = it
                tip = getTip(billAmount, 0.15, checked)
            },
            label = { Text("Bill Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
        )
        Spacer(modifier = Modifier.height(height = 30.dp))
        RoundUpTipRow(
            billAmount, checked = checked, onValueChange = { newChecked1, newTip ->
                checked = newChecked1; tip = newTip
            })
        Spacer(modifier = Modifier.height(height = 50.dp))
        Text(text = "Tip Amount: $${String.format("%.2f", tip)}", fontSize = 30.sp)
    }
}

@Composable
fun RoundUpTipRow(
    billAmount: String, checked: Boolean, onValueChange: (Boolean, Double) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Round up Tip?")
        Spacer(modifier = Modifier.width(width = 30.dp))
        Switch(
            checked = checked,
            onCheckedChange = {
                onValueChange(!checked, getTip(billAmount, 0.15, !checked))
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CustomTipTheme {
        TipScreen(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
        )
    }
}