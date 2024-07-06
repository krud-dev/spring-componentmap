package dev.krud.spring.componentmap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [ComponentMapAutoConfiguration::class, ComponentMapTestConfig::class])
class ComponentMapTest {

    @Autowired
    private lateinit var testImpl1: TestImpl1

    @Autowired
    private lateinit var testImpl2: TestImpl2

    @Autowired
    private lateinit var subImpl: SubImpl

    @Autowired
    private lateinit var subImpl2: SubImpl2

    @Autowired
    private lateinit var testImpl2Duplicate: TestImpl2Duplicate

    @Autowired
    private lateinit var testMapUser: TestMapUser

    @Test
    fun `context loads`() {
    }

    @Test
    fun `test component map populates correctly`() {
        val expected = mapOf(
            testImpl1.type to testImpl1,
            testImpl2.type to testImpl2
        )
        assertEquals(expected, testMapUser.map)
    }

    @Test
    fun `test component map of list populates correctly`() {
        val expected = mapOf(
            testImpl1.type to listOf(testImpl1),
            testImpl2.type to listOf(testImpl2, testImpl2Duplicate)
        )
        assertEquals(expected, testMapUser.multiMap)
    }

    @Test
    fun `subMap should populate`() {
        val expected = mapOf(
            subImpl.type to subImpl,
            subImpl2.type to subImpl2
        )
        assertEquals(expected, testMapUser.subMap)
    }
}