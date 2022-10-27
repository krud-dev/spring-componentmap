package dev.krud.spring.componentmap.examples.calculator

internal class DivideOperation : CalculatorOperation {
    override val type: CalculatorOperationType = CalculatorOperationType.DIVIDE

    override fun calculate(a: Int, b: Int): Number {
        return a / b
    }
}