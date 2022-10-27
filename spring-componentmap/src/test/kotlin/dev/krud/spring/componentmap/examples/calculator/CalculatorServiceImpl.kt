package dev.krud.spring.componentmap.examples.calculator

import dev.krud.spring.componentmap.ComponentMap

class CalculatorServiceImpl : CalculatorService {
    @ComponentMap
    private lateinit var operations: Map<CalculatorOperationType, CalculatorOperation>

    override fun calculate(type: CalculatorOperationType, a: Int, b: Int): Number {
        val operationHandler = operations[type] ?: throw IllegalArgumentException("Unknown operation type: $type")
        return operationHandler.calculate(a, b)
    }
}