package dev.krud.spring.componentmap.examples.calculator

interface CalculatorService {
    /**
     * Calculate the result of an operation.
     */
    fun calculate(type: CalculatorOperationType, a: Int, b: Int): Number
}