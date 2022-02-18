package br.hikarikun92.blogbackendheroku.post

import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_1
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_1_WITH_COMMENTS
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_2
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_2_WITH_COMMENTS
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_3
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_3_WITH_COMMENTS
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import java.time.LocalDateTime

@JooqTest
internal class PostRepositoryTest {
    @Autowired
    private lateinit var dsl: DSLContext

    private lateinit var repository: PostRepository

    @BeforeEach
    fun setup() {
        repository = PostRepository(dsl)
    }

    @Test
    fun `find by user ID`() {
        val user1Posts: List<Post> = repository.findByUserId(1)
            .collectList()
            .block()!!
        val expected1 = listOf<Post>()
        assertEquals(expected1, user1Posts)

        val user2Posts: List<Post> = repository.findByUserId(2)
            .collectList()
            .block()!!
        val expected2 = listOf(POST_1, POST_2)
        assertEquals(expected2, user2Posts)

        val user3Posts: List<Post> = repository.findByUserId(3)
            .collectList()
            .block()!!
        val expected3 = listOf(POST_3)
        assertEquals(expected3, user3Posts)
    }

    @Test
    fun `find by ID`() {
        val post1: Post? = repository.findById(1).block()
        assertEquals(POST_1_WITH_COMMENTS, post1)

        val post2: Post? = repository.findById(2).block()
        assertEquals(POST_2_WITH_COMMENTS, post2)

        val post3: Post? = repository.findById(3).block()
        assertEquals(POST_3_WITH_COMMENTS, post3)

        val nonExisting: Post? = repository.findById(10).block()
        assertNull(nonExisting)
    }

    @Test
    fun create() {
        val post = Post(null, "New post", "Some new post content", LocalDateTime.of(2022, 2, 16, 23, 33, 14), USER_2)
        val id: Int = repository.create(post).block()!!
        val expected: Post = post.copy(id = id, comments = setOf())

        val saved: Post = repository.findById(id).block()!!
        assertEquals(expected, saved)
    }
}
