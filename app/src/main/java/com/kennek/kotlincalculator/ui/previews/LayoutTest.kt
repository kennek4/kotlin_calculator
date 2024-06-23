package com.kennek.kotlincalculator.ui.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun LayoutTest() {
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
            CalculatorTextFields()
            CalculatorTopBar()
            CalculatorButtons()
        }
    }
}

@Composable
fun CalculatorTextFields() {
    Column (
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight(0.3f)
            .padding(top = 54.dp)
    ) {

        BasicTextField(
            value = "80084 + 1",
            onValueChange = {},
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

        BasicTextField(
            value = "80085",
            onValueChange = {},
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
fun CalculatorTopBar() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .background(Color.Cyan)
    ){

    }
}

@Composable
fun CalculatorButtons() {

    val buttonModifier: Modifier = Modifier
        .width(85.dp)
        .height(85.dp)

    val rowModifier: Modifier = Modifier
        .fillMaxWidth()

    Column (
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta)
    ) {
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "C")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "()")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "%")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "/")
            }
        }
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "7")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "8")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "9")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "x")
            }
        }
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "4")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "5")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "6")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "-")
            }
        }
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "1")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "2")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "3")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "+")
            }
        }
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "+/-")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "0")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = ".")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = buttonModifier,
                shape = CircleShape
            ) {
                Text(text = "=")
            }
        }
    }
}