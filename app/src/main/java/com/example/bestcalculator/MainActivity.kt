package com.example.bestcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
private fun CalculatorScreen() {
    var expression by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("0") }

    val rows = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("C", "0", "=", "+")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = expression.ifBlank { "Enter calculation" },
            modifier = Modifier.fillMaxWidth(),
            fontSize = 22.sp,
            textAlign = TextAlign.End
        )

        Text(
            text = output,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )

        rows.forEach { buttonRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                buttonRow.forEach { label ->
                    val useOutlined = label in listOf("C", "=", "/", "*", "-", "+")
                    val onClick = {
                        when (label) {
                            "C" -> {
                                expression = ""
                                output = "0"
                            }

                            "=" -> {
                                output = evaluateExpression(expression)
                            }

                            else -> {
                                expression += label
                            }
                        }
                    }

                    if (useOutlined) {
                        OutlinedButton(
                            onClick = onClick,
                            modifier = Modifier.size(width = 78.dp, height = 60.dp)
                        ) {
                            Text(text = label, fontSize = 20.sp)
                        }
                    } else {
                        Button(
                            onClick = onClick,
                            modifier = Modifier.size(width = 78.dp, height = 60.dp)
                        ) {
                            Text(text = label, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}

internal fun evaluateExpression(expression: String): String {
    if (expression.isBlank()) return "0"

    return try {
        val tokens = tokenize(expression)
        val postfix = infixToPostfix(tokens)
        val value = evaluatePostfix(postfix)
        if (value % 1.0 == 0.0) value.toLong().toString() else value.toString()
    } catch (_: Exception) {
        "Error"
    }
}

private fun tokenize(expression: String): List<String> {
    val tokens = mutableListOf<String>()
    var current = ""

    expression.forEach { ch ->
        if (ch.isDigit() || ch == '.') {
            current += ch
        } else if (ch in listOf('+', '-', '*', '/')) {
            if (current.isNotEmpty()) {
                tokens.add(current)
                current = ""
            }
            tokens.add(ch.toString())
        } else {
            throw IllegalArgumentException("Invalid character")
        }
    }

    if (current.isNotEmpty()) {
        tokens.add(current)
    }

    return tokens
}

private fun infixToPostfix(tokens: List<String>): List<String> {
    val output = mutableListOf<String>()
    val operators = mutableListOf<String>()

    fun precedence(op: String): Int = when (op) {
        "+", "-" -> 1
        "*", "/" -> 2
        else -> 0
    }

    tokens.forEach { token ->
        if (token.toDoubleOrNull() != null) {
            output.add(token)
        } else {
            while (operators.isNotEmpty() && precedence(operators.last()) >= precedence(token)) {
                output.add(operators.removeAt(operators.lastIndex))
            }
            operators.add(token)
        }
    }

    while (operators.isNotEmpty()) {
        output.add(operators.removeAt(operators.lastIndex))
    }

    return output
}

private fun evaluatePostfix(postfix: List<String>): Double {
    val stack = mutableListOf<Double>()

    postfix.forEach { token ->
        val number = token.toDoubleOrNull()
        if (number != null) {
            stack.add(number)
        } else {
            if (stack.size < 2) throw IllegalArgumentException("Invalid expression")
            val b = stack.removeAt(stack.lastIndex)
            val a = stack.removeAt(stack.lastIndex)
            val value = when (token) {
                "+" -> a + b
                "-" -> a - b
                "*" -> a * b
                "/" -> {
                    if (b == 0.0) throw ArithmeticException("Division by zero")
                    a / b
                }

                else -> throw IllegalArgumentException("Invalid operator")
            }
            stack.add(value)
        }
    }

    if (stack.size != 1) throw IllegalArgumentException("Invalid postfix")
    return stack.first()
}
