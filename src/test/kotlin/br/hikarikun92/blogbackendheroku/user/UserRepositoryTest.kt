package br.hikarikun92.blogbackendheroku.user

import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_1
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest

@JooqTest
internal class UserRepositoryTest {
    @Autowired
    private lateinit var dsl: DSLContext

    private lateinit var repository: UserRepository

    @BeforeEach
    fun setup() {
        repository = UserRepository(dsl)
    }

    @Test
    fun `find all`() {
        val users: List<User> = repository.findAll()
            .collectList()
            .block()!!
        val expected = listOf(USER_1, USER_2, USER_3)
        assertEquals(expected, users)
    }
}
