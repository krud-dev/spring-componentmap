package dev.krud.spring.componentmap

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order

interface TestHandler {
    @get:ComponentMapKey
    val type: String
}

class TestImpl1 : TestHandler {
    override val type: String
        get() = "Test1"
}

class TestImpl2 : TestHandler {
    override val type: String
        get() = "Test2"
}

class TestImpl2Duplicate : TestHandler {
    override val type: String
        get() = "Test2"
}

class TestMapUser {
    @ComponentMap
    lateinit var map: Map<String, TestHandler>

    @ComponentMap
    lateinit var multiMap: Map<String, List<TestHandler>>
}

@Configuration
class ComponentMapTestConfig {
    @Bean
    @Order(1)
    fun testImpl1() = TestImpl1()

    @Bean
    @Order(2)
    fun testImpl2() = TestImpl2()

    @Bean
    @Order(3)
    fun testImpl2Duplicate() = TestImpl2Duplicate()

    @Bean
    fun testMapUser() = TestMapUser()
}