package com.example.efficientcalculator

import androidx.collection.intIntMapOf
import java.util.Stack

class Expression(var infixExpression: MutableList<String>) {

    private fun infixToPostfix(): String {
        var result = ""
        val stack = Stack<String>()
        for (element in infixExpression) {
            if (element.all { it.isDigit() } || element.any { it == '.' }) {
                result += "$element "
            } else if (element == "(") {
                stack.push(element)
            } else if (element == ")") {
                while (stack.peek() != "(" && stack.isNotEmpty()) {
                    result += "${stack.pop()} "
                }
                if (stack.isNotEmpty()) {
                    stack.pop()
                }
            } else {
                while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(element)) {
                    result += "${stack.pop()} "
                }
            }

        }
        while (stack.isNotEmpty()) {
            result += "${stack.pop()} "
        }
        return result
    }

    private fun precedence(op: String): Int {
        return when (op) {
            "*", "/" -> 2
            "+", "-" -> 1
            else -> -1
        }
    }

    fun evaluateExpression(postfix: String): Number {
        val stack = Stack<Double>()
        var i = 0
        while (i < postfix.length) {

            if (postfix[i] == ' ') {
                i++
                continue
            } else if (Character.isDigit(postfix[i])) {
                var num = ""
                while (Character.isDigit(postfix[i]) || postfix[i] == '.') {
                    num += postfix[i]
                    i++
                }
                stack.push(num.toDouble())
            } else {
                val firstNum = stack.pop()
                val secondNum = stack.pop()
                when (postfix[i]) {
                    '*' -> stack.push(firstNum * secondNum)
                    '/' -> stack.push(firstNum / secondNum)
                    '+' -> stack.push(firstNum + secondNum)
                    '-' -> stack.push(firstNum - secondNum)
                }
            }
            i++
        }
        return if (stack.peek() / stack.peek().toInt() == 1.0) stack.peek()
            .toInt() else stack.peek()
    }
}