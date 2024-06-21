package com.kennek.kotlincalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){

        CalculatorTextFields(calculator)

        CalculatorTopRow(calculator)

        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        CalculatorButtons(
            calculator,
            Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
        )
    }
}

@Composable
private fun CalculatorTextFields(calculator: CalculatorViewModel) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        // Calculator expression field
        BasicTextField(
            value = calculator.calcString,
            onValueChange = {calculator.calcString = it},
            readOnly = true,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    bottom = 64.dp
                )
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        BasicTextField(
            value = calculator.previousAnswer,
            onValueChange = {calculator.previousAnswer = it},
            readOnly = true,
            maxLines = 1,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = MaterialTheme.typography.headlineSmall.fontWeight,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun CalculatorButtons(calculator: CalculatorViewModel, modifier: Modifier) {

    // First to Sixth rows in the calculator
    val firstRow = listOf(
        Pair("C") { calculator.clearResult() },
        Pair("()") { calculator.addOperator('(') },
        Pair("%") { calculator.addOperator('%') },
        Pair("÷") { calculator.clearEntry() },
    )
    val secondRow = listOf(
        Pair("7") { calculator.addNumber(7) },
        Pair("8") { calculator.addNumber(8) },
        Pair("9") { calculator.addNumber(9) },
        Pair("*") { calculator.addOperator('*') },
    )
    val thirdRow = listOf(
        Pair("4") { calculator.addNumber(4) },
        Pair("5") { calculator.addNumber(5) },
        Pair("6") { calculator.addNumber(6) },
        Pair("-") { calculator.addOperator('-') },
    )
    val fourthRow = listOf(
        Pair("1") { calculator.addNumber(1) },
        Pair("2") { calculator.addNumber(2) },
        Pair("3") { calculator.addNumber(3) },
        Pair("+") { calculator.addOperator('+') },
    )
    val fifthRow = listOf(
        Pair("+/-") { calculator.switchSign() },
        Pair("0") { calculator.addNumber(0) },
        Pair(".") { calculator.addDecimal() },
        Pair("=") { calculator.calculate() },
    )

    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        CalculatorRow(rowData = firstRow)
        CalculatorRow(rowData = secondRow)
        CalculatorRow(rowData = thirdRow)
        CalculatorRow(rowData = fourthRow)
        CalculatorRow(rowData = fifthRow)
    }
}

@Composable
fun CalculatorTopRow(calculator: CalculatorViewModel) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {

        Button(
            modifier = Modifier
                .weight(1f)
                .size(50.dp)
                .padding(8.dp)
            ,
            contentPadding = PaddingValues(0.dp),
            onClick = {}
        ) {
            Text(
                text = "A",
                style = MaterialTheme.typography.bodySmall,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .size(50.dp)
                .padding(8.dp)
            ,
            contentPadding = PaddingValues(0.dp),
            onClick = {}
        ) {
            Text(
                text = "B",
                style = MaterialTheme.typography.bodySmall,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }

        Spacer(
            modifier = Modifier
                .weight(3f)
        )

        Button(
            modifier = Modifier
                .weight(1f)
                .size(50.dp)
                .padding(8.dp)
            ,
            contentPadding = PaddingValues(0.dp),
            onClick = {calculator.clearEntry()}
        ) {
            Text(
                text = "⌫",
                style = MaterialTheme.typography.bodySmall,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
    }
}

@Composable
fun CalculatorRow(rowData: List<Pair<String, () -> Unit>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        // Creates a row of buttons
        for (pair: Pair<String, () -> Unit> in rowData) {
            val (buttonText, buttonFunction) = pair
            Button(
                modifier = Modifier
                    .weight(1f)
                    .size(100.dp)
                    .padding(12.dp)
                ,
                contentPadding = PaddingValues(0.dp),
                onClick = buttonFunction
            ) {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 32.sp
                )
            }
        }
    }
}


