package dev.krud.spring.componentmap.examples.calculator

internal class MultiplyOperation : CalculatorOperation {
    override val type: CalculatorOperationType = CalculatorOperationType.MULTIPLY

    override fun calculate(a: Int, b: Int): Number {
        return a * b
    }
}