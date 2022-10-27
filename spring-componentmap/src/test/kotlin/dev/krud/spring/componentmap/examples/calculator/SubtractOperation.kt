package dev.krud.spring.componentmap.examples.calculator

internal class SubtractOperation : CalculatorOperation {
    override val type: CalculatorOperationType = CalculatorOperationType.SUBTRACT

    override fun calculate(a: Int, b: Int): Number {
        return a - b
    }
}