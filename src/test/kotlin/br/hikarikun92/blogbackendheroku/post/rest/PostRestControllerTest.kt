package br.hikarikun92.blogbackendheroku.post.rest

import br.hikarikun92.blogbackendheroku.comment.Comment
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_1
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_1_WITH_COMMENTS
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_2
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_2_WITH_COMMENTS
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_3
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_3_WITH_COMMENTS
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_1
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import br.hikarikun92.blogbackendheroku.post.Post
import br.hikarikun92.blogbackendheroku.user.User
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.format.DateTimeFormatter

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class PostRestControllerTest {
    @Autowired
    private lateinit var client: WebTestClient

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    @Test
    fun `find by User ID`() {
        fun Post.toJson() = "{\"id\":$id,\"title\":\"$title\",\"body\":\"$body\",\"publishedDate\":\"${
            dateTimeFormatter.format(publishedDate)
        }\"}"

        val expectedBody1 = "[]"

        client.get()
            .uri("/users/{userId}/posts", USER_1.id)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(expectedBody1)

        val expectedBody2 = "[" +
                "${POST_1.toJson()}," +
                POST_2.toJson() +
                "]"

        client.get()
            .uri("/users/{userId}/posts", USER_2.id)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(expectedBody2)

        val expectedBody3 = "[" +
                POST_3.toJson() +
                "]"

        client.get()
            .uri("/users/{userId}/posts", USER_3.id)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(expectedBody3)
    }

    @Test
    fun `find by ID`() {
        fun User.toJson() = "{\"id\":$id,\"username\":\"$username\"}"
        fun Comment.toJson() =
            "{\"id\":$id,\"title\":\"$title\",\"body\":\"$body\",\"publishedDate\":\"${
                dateTimeFormatter.format(publishedDate)
            }\",\"user\":${user.toJson()}}"

        fun Post.toJson() =
            "{\"id\":$id,\"title\":\"$title\",\"body\":\"$body\",\"user\":${user.toJson()},\"publishedDate\":\"${
                dateTimeFormatter.format(publishedDate)
            }\",\"comments\":${comments!!.joinToString(separator = ",", prefix = "[", postfix = "]") { it.toJson() }}}"

        client.get()
            .uri("/posts/{id}", POST_1_WITH_COMMENTS.id)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(POST_1_WITH_COMMENTS.toJson())

        client.get()
            .uri("/posts/{id}", POST_2_WITH_COMMENTS.id)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(POST_2_WITH_COMMENTS.toJson())

        client.get()
            .uri("/posts/{id}", POST_3_WITH_COMMENTS.id)
            .exchange()
            .expectStatus().isOk
            .expectBody().json(POST_3_WITH_COMMENTS.toJson())

        client.get()
            .uri("/posts/{id}", 10)
            .exchange()
            .expectStatus().isNotFound
    }
}
