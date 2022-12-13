package br.hikarikun92.blogbackendheroku.user.rest

import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_1
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import br.hikarikun92.blogbackendheroku.user.User
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
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

    @Test
    fun `login with non-existing username`() {
        client.post()
            .uri("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"username\":\"Non-existing\",\"password\":\"PaSsW0rD!\"}")
            .exchange()
            .expectStatus().isUnauthorized
            .expectHeader().valueEquals(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
    }

    @Test
    fun `login with wrong password`() {
        client.post()
            .uri("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"username\":\"John Doe\",\"password\":\"Wr0nG\"}")
            .exchange()
            .expectStatus().isUnauthorized
            .expectHeader().valueEquals(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
    }

    @Test
    fun `login with success`() {
        client.post()
            .uri("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"username\":\"John Doe\",\"password\":\"PaSsW0rD!\"}")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .value(Matchers.notNullValue())
    }
}
