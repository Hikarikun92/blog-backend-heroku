package br.hikarikun92.blogbackendheroku.user

import br.hikarikun92.blogbackendheroku.factory.UserCredentialsFactory.Companion.USER_CREDENTIALS_1
import br.hikarikun92.blogbackendheroku.factory.UserCredentialsFactory.Companion.USER_CREDENTIALS_2
import br.hikarikun92.blogbackendheroku.factory.UserCredentialsFactory.Companion.USER_CREDENTIALS_3
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_1
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import br.hikarikun92.blogbackendheroku.user.jpa.UserJpaRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest

@DataR2dbcTest
internal class UserRepositoryTest {
    @Autowired
    private lateinit var jpaRepository: UserJpaRepository

    private lateinit var repository: UserRepository

    @BeforeEach
    fun setup() {
        repository = UserRepository(jpaRepository)
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
