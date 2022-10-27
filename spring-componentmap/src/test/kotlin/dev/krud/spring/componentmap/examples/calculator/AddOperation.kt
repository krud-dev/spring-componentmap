package dev.krud.spring.componentmap.examples.calculator

internal class AddOperation : CalculatorOperation {
    override val type: CalculatorOperationType = CalculatorOperationType.ADD

    override fun calculate(a: Int, b: Int): Number {
        return a + b
    }
}