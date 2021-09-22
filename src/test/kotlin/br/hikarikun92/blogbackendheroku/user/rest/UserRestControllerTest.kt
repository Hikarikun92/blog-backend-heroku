package br.hikarikun92.blogbackendheroku.user.rest

import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_1
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class UserRestControllerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun `find all`() {
        val expectedBody = "[" +
                "{\"id\":${USER_1.id},\"username\":\"${USER_1.username}\"}," +
                "{\"id\":${USER_2.id},\"username\":\"${USER_2.username}\"}," +
                "{\"id\":${USER_3.id},\"username\":\"${USER_3.username}\"}" +
                "]"

        client.get()
            .uri("/users")
            .exchange()
            .expectStatus().isOk
            .expectBody().json(expectedBody)
    }
}