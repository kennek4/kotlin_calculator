package com.kennek.kotlincalculator.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import kotlin.math.sqrt

private const val OPERATORS: String = "/+*%-"

/**
 * Determines if a given string is of a float type
 */
private fun isStringFloat(string: String): Boolean {
    return string.contains(".")
}

/**
 * Determines if a given string contains an operator character
 *
 * Operators are defined in the OPERATORS const value
 */
private fun stringHasOperator(string: String) : Boolean {
    return OPERATORS.contains(string)
}

/**
 * Given an infix formatted string, evaluates the string and returns the result
 */
private fun infixEvaluation(calcString: String): Number {

    val splitCalcString = calcString.split(" ")
    val operatorStack = ArrayDeque<String>()
    val operandStack = ArrayDeque<Number>()

    for (item: String in splitCalcString) {
        if (!stringHasOperator(item)) {

            // Convert the operand into the respective number type, float or int
            val itemAsNumber: Number =
                if (isStringFloat(item)) item.toFloat() else item.toInt()

            operandStack.addLast(itemAsNumber)

        } else if (stringHasOperator(item)) {
            operatorStack.addLast(item)
        }
    }

    // At the end of the infix evaluation, the first element of operandStack will be the answer
    return operandStack.first()
}

class CalculatorViewModel : ViewModel() {

    var calcString: String by mutableStateOf("")
    var answer: String by mutableStateOf("")

    /**
     * Computes the calculation provided by the user
     */
    fun calculate() {
        if (calcString.isEmpty()) return // Can't calculate an empty string

        answer = infixEvaluation(calcString).toString()
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

        if (calcString.isNotEmpty() && !calcString.isDigitsOnly()) {
            calculate()
        }
    }

    fun addDecimal() {
    }

    fun addOperator(operator: String) {
        // Can't add operator if no operands are present in the calculation
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