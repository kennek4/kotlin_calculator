package com.kennek.kotlincalculator.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kennek.kotlincalculator.data.CalculatorData
import kotlin.math.sqrt

class CalculatorViewModel : ViewModel() {


    private val calcData by mutableStateOf(CalculatorData(""))
    var calcString: String = calcData.calcString

    private fun isStringFloat(string: String): Boolean {
        return string.contains(".")
    }

    fun calculate() {
        if (calcString.isEmpty()) return

        val splitCalcString = calcString.split(" ")
        val operatorStack = ArrayDeque<Char>()
        val operandStack = ArrayDeque<Number>()

        for (item in splitCalcString) {
            println(item)
        }
    }

    fun exponent() {

    }

    fun reciprocal() {

    }

    fun squareRoot() {
        val resultAsNumber = calcString.toFloat()
        calcString = sqrt(resultAsNumber).toString()
    }

    /**
     * Switches the sign of the current result
     */
    fun switchSign() {
        var resultAsNumber = calcString.toFloat()
        resultAsNumber *= -1
        calcString = resultAsNumber.toString()
    }

    /**
     * Adds an operand to the operand string for later calculations
     */
    fun addOperand(operand: Int) {
        calcString += operand
    }

    fun addDecimal() {
    }

    fun addOperator(operator: String) {
        if (calcString.isEmpty()) return

        val validAction = calcString.length > 1 && calcString.dropLast(1).last() in "+/*-%".toSet()

        if (validAction) {
            println("Invalid action.")
        } else {
            calcString += operator
        }
    }

    /**
     * Clears the entire calculation
     */
    fun clearResult() {
        calcString = ""
    }

    /**
     * Clears the last input provided by the user
     */
    fun clearEntry() {
        if (calcString.isEmpty()) return
        calcString = if (calcString.length > 1 && calcString.last().toString().isBlank()) {
            calcString.dropLast(3)
        } else {
            calcString.dropLast(1)
        }
    }
}