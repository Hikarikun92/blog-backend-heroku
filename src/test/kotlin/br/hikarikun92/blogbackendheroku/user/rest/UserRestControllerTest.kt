package br.hikarikun92.blogbackendheroku.user.rest

import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_1
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import br.hikarikun92.blogbackendheroku.user.User
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
        fun User.toJson() = "{\"id\":$id,\"username\":\"$username\"}"

        val expectedBody = "[" +
                "${USER_1.toJson()}," +
                "${USER_2.toJson()}," +
                USER_3.toJson() +
                "]"

        client.get()
            .uri("/users")
            .exchange()
            .expectStatus().isOk
            .expectBody().json(expectedBody)
    }
}
