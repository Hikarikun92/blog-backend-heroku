package br.hikarikun92.blogbackendheroku.post.rest

import br.hikarikun92.blogbackendheroku.comment.Comment
import br.hikarikun92.blogbackendheroku.config.FixedClockConfig
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_1
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_1_WITH_COMMENTS
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_2
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_2_WITH_COMMENTS
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_3
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_3_WITH_COMMENTS
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_1
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.Comment.Companion.COMMENT
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.Post.Companion.POST
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records.PostRecord
import br.hikarikun92.blogbackendheroku.post.Post
import br.hikarikun92.blogbackendheroku.security.JwtTokenProvider
import br.hikarikun92.blogbackendheroku.user.User
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Mono
import java.time.Clock
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(FixedClockConfig::class)
@Transactional
@AutoConfigureTestDatabase
internal class PostRestControllerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @Autowired
    private lateinit var clock: Clock

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var dsl: DSLContext

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

    @DirtiesContext  //Needed because WebTestClient runs outside of the server transaction, which causes it to be committed instead of rolled back
    @Test
    fun create() {
        val token: String = tokenProvider.generateToken(USER_2.username)

        val body = "{\"title\":\"New article\",\"body\":\"Some new content\"}"

        val location: String = client.post()
            .uri("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            .body(BodyInserters.fromPublisher(Mono.just(body), String::class.java))
            .exchange()
            .expectStatus().isCreated
            .expectBody().isEmpty
            .responseHeaders.location!!.toString()

        val id: Int = getIdFromLocation(location)
        assertEquals("/posts/$id", location)

        val postRecord: PostRecord = dsl.fetchSingle(POST, POST.ID.eq(id))
        assertEquals("New article", postRecord.title)
        assertEquals("Some new content", postRecord.body)
        assertEquals(LocalDateTime.now(clock), postRecord.publishedDate)
        assertEquals(USER_2.id, postRecord.userId)

        val comments: Int = dsl.fetchCount(COMMENT, COMMENT.POST_ID.eq(id))
        assertEquals(0, comments)
    }

    private fun getIdFromLocation(location: String): Int {
        val index = location.lastIndexOf('/') + 1
        return location.substring(index).toInt()
    }
}
