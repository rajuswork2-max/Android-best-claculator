package com.example.bestcalculator

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorEvaluatorTest {

    @Test
    fun evaluatesOperatorPrecedence() {
        assertEquals("14", evaluateExpression("2+3*4"))
    }

    @Test
    fun evaluatesDivisionAndFormatting() {
        assertEquals("2.5", evaluateExpression("5/2"))
    }

    @Test
    fun invalidInputReturnsError() {
        assertEquals("Error", evaluateExpression("5+"))
    }
}
