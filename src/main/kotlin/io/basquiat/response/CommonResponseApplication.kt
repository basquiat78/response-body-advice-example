package io.basquiat.response

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class CommonResponseApplication

fun main(args: Array<String>) {
	runApplication<CommonResponseApplication>(*args)
}
