package dev.krud.spring.componentmap.examples.calculator

import dev.krud.spring.componentmap.ComponentMapAutoConfiguration
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo

@ExtendWith(SpringExtension::class)
@Import(CalculatorServiceTest.Config::class)
@ImportAutoConfiguration(ComponentMapAutoConfiguration::class)
internal class CalculatorServiceTest {
    @Autowired
    private lateinit var calculatorService: CalculatorService

    @Test
    internal fun `add operation adds 2 numbers`() {
        val result = calculatorService.calculate(CalculatorOperationType.ADD, 1, 2)
        expectThat(result)
            .isEqualTo(3)
    }

    @Test
    internal fun `subtract operation subtracts 2 numbers`() {
        val result = calculatorService.calculate(CalculatorOperationType.SUBTRACT, 1, 2)
        expectThat(result)
            .isEqualTo(-1)
    }

    @Test
    internal fun `multiply operation multiplies 2 numbers`() {
        val result = calculatorService.calculate(CalculatorOperationType.MULTIPLY, 2, 2)
        expectThat(result)
            .isEqualTo(4)
    }

    @Test
    internal fun `divide operation divides 2 numbers`() {
        val result = calculatorService.calculate(CalculatorOperationType.DIVIDE, 4, 2)
        expectThat(result)
            .isEqualTo(2)
    }

    @Test
    internal fun `invalid operation should throw exception`() {
        expectThrows<IllegalArgumentException> {
            calculatorService.calculate(CalculatorOperationType.NONE, 1, 2)
        }
    }

    @Configuration
    internal class Config {
        @Bean
        fun addOperation() = AddOperation()

        @Bean
        fun subtractOperation() = SubtractOperation()

        @Bean
        fun multiplyOperation() = MultiplyOperation()

        @Bean
        fun divideOperation() = DivideOperation()

        @Bean
        fun calculatorService(): CalculatorService = CalculatorServiceImpl()
    }
}