package br.hikarikun92.blogbackendheroku.post.rest

import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_1
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_2
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_3
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
internal class PostRestControllerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun `find by User ID`() {
        val expectedBody1 = "[]"

        client.get()
            .uri("/users/{userId}/posts", USER_1.id)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(expectedBody1)

        val expectedBody2 = "[" +
                "{\"id\":${POST_1.id},\"title\":\"${POST_1.title}\",\"body\":\"${POST_1.body}\",\"user\":{\"id\":${USER_2.id},\"username\":\"${USER_2.username}\"}}," +
                "{\"id\":${POST_2.id},\"title\":\"${POST_2.title}\",\"body\":\"${POST_2.body}\",\"user\":{\"id\":${USER_2.id},\"username\":\"${USER_2.username}\"}}" +
                "]"

        client.get()
            .uri("/users/{userId}/posts", USER_2.id)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(expectedBody2)

        val expectedBody3 = "[" +
                "{\"id\":${POST_3.id},\"title\":\"${POST_3.title}\",\"body\":\"${POST_3.body}\",\"user\":{\"id\":${USER_3.id},\"username\":\"${USER_3.username}\"}}" +
                "]"

        client.get()
            .uri("/users/{userId}/posts", USER_3.id)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(expectedBody3)
    }
}