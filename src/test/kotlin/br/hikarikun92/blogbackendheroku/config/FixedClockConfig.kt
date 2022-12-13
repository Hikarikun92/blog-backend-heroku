package br.hikarikun92.blogbackendheroku.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset

@TestConfiguration
class FixedClockConfig {
    /**
     * Replace the default (system) clock with one fixed at 2022-01-06 midnight UTC.
     *
     * @return A fixed clock in the specified time.
     */
    @Bean
    fun clock(): Clock {
        val dateTime = LocalDateTime.of(2022, 1, 6, 0, 0)
        return Clock.fixed(dateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
    }
}