package com.teamsparta.exhibitionnewsfeed

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class ExhibitionNewsfeedApplication

fun main(args: Array<String>) {
    runApplication<ExhibitionNewsfeedApplication>(*args)
}
