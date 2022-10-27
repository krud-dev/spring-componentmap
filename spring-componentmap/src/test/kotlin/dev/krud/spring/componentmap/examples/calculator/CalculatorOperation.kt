package dev.krud.spring.componentmap.examples.calculator

import dev.krud.spring.componentmap.ComponentMapKey

internal interface CalculatorOperation {
    @get:ComponentMapKey
    val type: CalculatorOperationType

    /**
     * Calculate the result of an operation.
     */
    fun calculate(a: Int, b: Int): Number
}