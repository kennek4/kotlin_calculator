package com.kennek.kotlincalculator.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

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
 * Determines if a given string is of a Float type
 */
private fun isStringFloat(string: String): Boolean {
    return string.toFloatOrNull() != null
}

/**
 * Determines if a given string is of an Int type
 */
private fun isStringInt(string: String): Boolean {
    return string.toIntOrNull() != null
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

    println("$numOne $operator $numTwo = $answer`")
    return answer
}

private fun postfixEvaluation(postFixArray: Array<String>): Number {

    val output = ArrayDeque<String>()

    for (element: String in postFixArray) {
        if (isStringFloat(element) || isStringInt(element)) {
            output.addLast(element)
        } else {
            val numTwo = output.removeLast().toFloat()
            val numOne = output.removeLast().toFloat()

            val answer = evaluateExpression(numOne, element, numTwo)
            output.addLast(answer.toString())
        }
    }

    return output.removeLast().toFloat()
}

private fun infixToPostfix(calcString: String): Array<String> {

    val splitCalcString: List<String> = calcString.split(" ")
    val output = ArrayDeque<String>()
    val operators = ArrayDeque<String>()

    for (token: String in splitCalcString) {
        if (isStringFloat(token) || isStringInt(token)) {
            output.addLast(token)
        }
        else if (token == "(") {
            operators.addLast(token)
        }
        else if (token == ")") {
            while (operators.isNotEmpty() && (operators.last() != "(")) {
                output.addLast(operators.removeLast())
            }

            operators.removeLast()
        } else {
            while (operators.isNotEmpty() && operatorPrecedence(operators.last()) >= operatorPrecedence(token)) {
                output.addLast(operators.removeLast())
            }
            operators.addLast(token)
        }
    }

    while (operators.isNotEmpty()) {
        output.addLast(operators.removeLast())
    }

    return output.toTypedArray<String>()
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
    }

    fun calculate() {
        if (lastIsOperator || calcString.isEmpty()) return // Invalid format error

        val postFixArray: Array<String> = infixToPostfix(calcString)
        previousAnswer = postfixEvaluation(postFixArray).toString()
    }

    fun addDecimal() {
        if (calcString.isEmpty()) return
        calcString += '.'
    }

    fun clearEntry() {
        if (calcString.isEmpty()) return

        if (lastIsOperator) {

            calcString = calcString.dropLast(3)
            lastIsOperator = false

        } else {

            if (calcString.dropLast(1).isEmpty()) {
                calcString = ""
                return
            }

            else {
                calcString = calcString.dropLast(1)

                val lastChar: Char = calcString.last()
                if (lastChar == ' ') { // A space will always pad around an operator char
                    lastIsOperator = true
                }
            }
        }
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