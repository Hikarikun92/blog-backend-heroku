package br.hikarikun92.blogbackendheroku.post

import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.*
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
        println(user1Posts)

        val user2Posts: List<Post> = repository.findByUserId(2)
            .collectList()
            .block()!!
        println(user2Posts)

        val user3Posts: List<Post> = repository.findByUserId(3)
            .collectList()
            .block()!!
        println(user3Posts)
    }
}