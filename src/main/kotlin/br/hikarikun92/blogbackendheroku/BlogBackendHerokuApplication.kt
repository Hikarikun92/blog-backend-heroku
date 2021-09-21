package br.hikarikun92.blogbackendheroku

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogBackendHerokuApplication

fun main(args: Array<String>) {
	runApplication<BlogBackendHerokuApplication>(*args)
}
