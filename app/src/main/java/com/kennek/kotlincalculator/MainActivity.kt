package com.kennek.kotlincalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kennek.kotlincalculator.viewmodels.CalculatorViewModel

class MainActivity : ComponentActivity() {

    private val calculatorViewModel by viewModels<CalculatorViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculator(calculatorViewModel)
        }
    }
}

@Composable
fun Calculator(calculator: CalculatorViewModel) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ){

        // Calculator Result Field
        OutlinedTextField(
            value = calculator.calcString,
            onValueChange = {calculator.calcString = it},
            readOnly = true,
            modifier = Modifier
                .padding(16.dp)
        )

        // First to Sixth rows in the calculator
        val firstRow = listOf<Pair<String, () -> Unit>>(
            Pair("%") { calculator.addOperator(" % ") },
            Pair("CE") { calculator.clearEntry() },
            Pair("C") {calculator.clearResult()},
            Pair("<-") {calculator.clearEntry()},
        )
        val secondRow = listOf<Pair<String, () -> Unit>>(
            Pair("1/x") { calculator.reciprocal() },
            Pair("EXP") { calculator.exponent() },
            Pair("SQRT") {calculator.squareRoot()},
            Pair("/") {calculator.addOperator(" / ")},
        )
        val thirdRow = listOf<Pair<String, () -> Unit>>(
            Pair("7") { calculator.addOperand(7) },
            Pair("8") { calculator.addOperand(8) },
            Pair("9") {calculator.addOperand(9)},
            Pair("*") {calculator.addOperator(" * ")},
        )
        val fourthRow = listOf<Pair<String, () -> Unit>>(
            Pair("4") { calculator.addOperand(4) },
            Pair("5") { calculator.addOperand(5) },
            Pair("6") {calculator.addOperand(6)},
            Pair("-") {calculator.addOperator(" - ")},
        )
        val fifthRow = listOf<Pair<String, () -> Unit>>(
            Pair("1") { calculator.addOperand(1) },
            Pair("2") { calculator.addOperand(2) },
            Pair("3") {calculator.addOperand(3)},
            Pair("+") {calculator.addOperator(" + ")},
        )
        val sixthRow = listOf<Pair<String, () -> Unit>>(
            Pair("+/-") { calculator.switchSign()},
            Pair("0") { calculator.addOperand(0) },
            Pair(".") {calculator.addDecimal()},
            Pair("=") {calculator.calculate()},
        )

        CalculatorRow(rowData = firstRow, calculator = calculator )
        CalculatorRow(rowData = secondRow, calculator = calculator )
        CalculatorRow(rowData = thirdRow, calculator = calculator )
        CalculatorRow(rowData = fourthRow, calculator = calculator )
        CalculatorRow(rowData = fifthRow, calculator = calculator )
        CalculatorRow(rowData = sixthRow, calculator = calculator )
    }
}

@Composable
fun CalculatorRow(rowData: List<Pair<String, () -> Unit>>, calculator: CalculatorViewModel) {
    Row (
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                end = 16.dp,
                start = 16.dp
            )
    )
    {
        // Creates a row of buttons
        for (pair: Pair<String, () -> Unit> in rowData) {
            val (buttonText, buttonFunction) = pair
            Button(
                shape = AbsoluteCutCornerShape(0.dp),
                modifier = Modifier
                    .weight(1f),
                onClick = buttonFunction
            ) {
                Text(text = buttonText)
            }
        }
    }
}


