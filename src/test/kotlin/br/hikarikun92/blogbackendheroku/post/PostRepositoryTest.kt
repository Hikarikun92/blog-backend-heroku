package br.hikarikun92.blogbackendheroku.post

import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_1
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_2
import br.hikarikun92.blogbackendheroku.factory.PostFactory.Companion.POST_3
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest

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
}