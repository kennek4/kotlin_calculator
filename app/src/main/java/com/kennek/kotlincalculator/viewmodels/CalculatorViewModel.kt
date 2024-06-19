package com.kennek.kotlincalculator.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import kotlin.math.sqrt

/**
  * Defines what classifies as an "operator"
  */
private const val OPERATORS: String = "/+*%-"

/**
 * Mathematical operator precedence
 */
private val PRECEDENCE_MAPPING: Map<String, Int> = mapOf(
    "(" to 3,
    ")" to 3,
    "%" to 2,
    "/" to 2,
    "*" to 2,
    "+" to 1,
    "-" to 1
)

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
 * Returns the operator's precedence represented as an Int.
 *
 * The higher the Int value, the higher the precedence.
 * The precedence of operators are defined in the PRECEDENCE_MAPPING value
 */
private fun operatorPrecedence(operator: String): Int {
    return PRECEDENCE_MAPPING[operator]!!
}

/**
 * Evaluates a mathematical expression when given 2 operands (operandOne and operandTwo respectively) and an operator
 *
 * Returns a value of Number type
 */
private fun evaluateExpression(operandOne: Float, operator: String, operandTwo: Float): Number {

    var answer: Number = 0

    when (operator) {
        "%" -> answer = operandOne % operandTwo
        "/" -> answer = operandOne / operandTwo
        "*" -> answer = operandOne * operandTwo
        "+" -> answer = operandOne + operandTwo
        "-" -> answer = operandOne - operandTwo
    }

    return answer
}

/**
 * Given an infix formatted string, evaluates the string and returns the result
 */
private fun infixEvaluation(calcString: String): Number {

    val splitCalcString: List<String> = calcString.split(" ")
    val operatorStack = ArrayDeque<String>()
    val operandStack = ArrayDeque<Number>()

    fun evaluateStacks() = run {
        val operandTwo = operandStack.removeLast().toFloat()
        val operandOne = operandStack.removeLast().toFloat()
        val operator: String = operatorStack.removeLast()

        val answer = evaluateExpression(operandOne, operator, operandTwo)
        operandStack.addLast(answer)
    }

    for (item: String in splitCalcString) {

        // Item is an operand
        if (!stringHasOperator(item)) {

            // Convert the operand into the respective number type, float or int
            val itemAsNumber: Number =
                if (isStringFloat(item)) item.toFloat() else item.toInt()

            operandStack.addLast(itemAsNumber)

        } else if (stringHasOperator(item)) { // Item is an operator
            if (operatorStack.isEmpty()) {
                operatorStack.addLast(item)
            } else {
                val top: String = operatorStack.last()
                if (operatorPrecedence(item) >= operatorPrecedence(top)) {
                    operatorStack.addLast(item)
                }
            }
        } else {
            evaluateStacks()
        }
        // Add bracket functionality here
    }

    // Evaluates stacks until the operatorStack is empty
    do {
        evaluateStacks()
    } while (operatorStack.isNotEmpty())

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

        val result = infixEvaluation(calcString).toString()
        answer = if (!isStringFloat(result)) result.toInt().toString() else result.toFloat().toString()
    }

    /**
     * Applies an exponent to the current operand
     */
    fun exponent() {

    }

    /**
     * The current operand is changed to its reciprocal
     */
    fun reciprocal() {

    }

    /**
     * The current operand has a square root operation applied to it
     */
    fun squareRoot() {
        if (calcString.isEmpty()) return
        val resultAsNumber = calcString.toFloat()
        calcString = sqrt(resultAsNumber).toString()
    }

    /**
     * Switches the sign of the current result
     */
    fun switchSign() {
        if (calcString.isEmpty()) return
        var resultAsNumber = answer.toFloat()
        resultAsNumber *= -1
        answer = resultAsNumber.toString()
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

    /**
     * Adds a given operator to the calcString
     */
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
        answer = ""
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

        calculate()
    }
}