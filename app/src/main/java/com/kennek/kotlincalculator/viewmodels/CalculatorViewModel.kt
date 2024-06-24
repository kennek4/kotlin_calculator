package com.kennek.kotlincalculator.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


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
private fun evaluateExpression(numOne: Float, operator: String, numTwo: Float): Number {

    var answer: Number = 0

    when (operator) {
        "%" -> answer = numOne % numTwo
        "/" -> answer = numOne / numTwo
        "*" -> answer = numOne * numTwo
        "+" -> answer = numOne + numTwo
        "-" -> answer = numOne - numTwo
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


private fun numIsDecimal(num: String): Boolean {
    return num.toFloatOrNull() != null
}


class CalculatorViewModel : ViewModel() {

    private var lastIsOperator: Boolean = false // determines if the last input was an operator or not
    var calcString: String by mutableStateOf("")
    var previousAnswer: String by mutableStateOf("")

    fun addOperator(operator: Char) {
        // Can't add an operator to an empty expression
        if (calcString.isEmpty() || lastIsOperator) return

        calcString += " $operator "
        lastIsOperator = true
    }

    fun addNumber(num: Byte) {
        calcString += num
        lastIsOperator = false
        calculate()
    }

    fun calculate() {
        if (lastIsOperator || calcString.isEmpty()) return // Invalid format error
        val answer: String = infixEvaluation(calcString).toString()
        previousAnswer = if (numIsDecimal(answer)) answer.toFloat().toString() else answer.toInt().toString()
    }

    fun addDecimal() {
        if (calcString.isEmpty()) return
        calcString += '.'
    }

    fun clearEntry() {
        calcString = calcString.dropLast(1)
    }

    fun clearResult() {
        calcString = ""
        previousAnswer = ""
        lastIsOperator = false
    }

    fun switchSign() {
        TODO("Not yet implemented")
    }

}