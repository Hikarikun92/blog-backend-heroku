package br.hikarikun92.blogbackendheroku.user

import br.hikarikun92.blogbackendheroku.config.JooqAutoConfiguration
import br.hikarikun92.blogbackendheroku.factory.UserCredentialsFactory.Companion.USER_CREDENTIALS_1
import br.hikarikun92.blogbackendheroku.factory.UserCredentialsFactory.Companion.USER_CREDENTIALS_2
import br.hikarikun92.blogbackendheroku.factory.UserCredentialsFactory.Companion.USER_CREDENTIALS_3
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_1
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.boot.test.autoconfigure.jooq.AutoConfigureJooq
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

//@JooqTest
@ExtendWith(SpringExtension::class)
@AutoConfigureJooq
@Import(JooqAutoConfiguration::class, R2dbcAutoConfiguration::class, FlywayAutoConfiguration::class)
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

    @Test
    fun `find credentials by username`() {
        val credentials1: UserCredentials? = repository.findCredentialsByUsername(USER_1.username)
            .block()
        assertEquals(USER_CREDENTIALS_1, credentials1)

        val credentials2: UserCredentials? = repository.findCredentialsByUsername(USER_2.username)
            .block()
        assertEquals(USER_CREDENTIALS_2, credentials2)

        val credentials3: UserCredentials? = repository.findCredentialsByUsername(USER_3.username)
            .block()
        assertEquals(USER_CREDENTIALS_3, credentials3)

        val nonExisting: UserCredentials? = repository.findCredentialsByUsername("non-existing")
            .block()
        assertNull(nonExisting)
    }
}
