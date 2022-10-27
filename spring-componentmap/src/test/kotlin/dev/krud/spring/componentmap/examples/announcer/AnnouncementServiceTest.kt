package dev.krud.spring.componentmap.examples.announcer

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
import strikt.assertions.isEqualTo

@ExtendWith(SpringExtension::class)
@Import(AnnouncementServiceTest.Config::class)
@ImportAutoConfiguration(ComponentMapAutoConfiguration::class)
internal class AnnouncementServiceTest {
    @Autowired
    private lateinit var announcementService: AnnouncementService

    @Test
    internal fun `announcement service should announce to subscribers`() {
        val announcement = Announcement(AnnouncementType.USER_LOGGED_IN, "test")
        val result = announcementService.announce(announcement)
        expectThat(result)
            .isEqualTo(3)
    }

    @Configuration
    internal class Config {
        @Bean
        fun announcementService() = AnnouncementServiceImpl()

        @Bean
        fun userLoggedInSubscriber1() = UserLoggedInSubscriber1()

        @Bean
        fun userLoggedInSubscriber2() = UserLoggedInSubscriber2()

        @Bean
        fun userLoggedInSubscriber3() = UserLoggedInSubscriber3()
    }
}