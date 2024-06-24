package com.kennek.kotlincalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            Box(modifier = Modifier.safeDrawingPadding()) {
                Calculator(calculator = calculatorViewModel)
            }
        }
    }
}

@Composable
fun Calculator(calculator: CalculatorViewModel) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
        ) {
            CalculatorTextFields(calculator)
            CalculatorTopRow()
            CalculatorButtons(calculator)
        }
    }
}

@Composable
private fun CalculatorTextFields(calculator: CalculatorViewModel) {
    Column (
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight(0.3f)
            .padding(top = 54.dp)
    ) {

        // Calculator Expression
        BasicTextField(
            readOnly = true,
            value = calculator.calcString,
            onValueChange = {calculator.calcString = it},
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End,
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal
            )
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        )

        // Answer Text Field
        BasicTextField(
            readOnly = true,
            value = calculator.previousAnswer,
            onValueChange = {calculator.previousAnswer = it},
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
            ,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End,
                fontSize = 24.sp,
                fontWeight = FontWeight.Thin
            )
        )
    }
}

@Composable
private fun CalculatorButtons(calculator: CalculatorViewModel) {

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

    Column (
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        CalculatorRow(rowData = firstRow)
        CalculatorRow(rowData = secondRow)
        CalculatorRow(rowData = thirdRow)
        CalculatorRow(rowData = fourthRow)
        CalculatorRow(rowData = fifthRow)
    }
}

@Composable
fun CalculatorTopRow() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .background(Color.Cyan)
    ){

    }
}

@Composable
fun CalculatorRow(rowData: List<Pair<String, () -> Unit>>) {

    val buttonModifier: Modifier = Modifier
        .width(85.dp)
        .height(85.dp)

    val rowModifier: Modifier = Modifier
        .fillMaxWidth()

    Row(
        modifier = rowModifier,
        horizontalArrangement = Arrangement.SpaceBetween
        )
    {
        // Creates a row of buttons
        for (pair: Pair<String, () -> Unit> in rowData) {
            val (buttonText, buttonFunction) = pair
            Button(
                modifier = buttonModifier,
                contentPadding = PaddingValues(0.dp),
                shape = CircleShape,
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


