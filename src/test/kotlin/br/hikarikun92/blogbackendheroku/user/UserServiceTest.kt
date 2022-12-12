package br.hikarikun92.blogbackendheroku.user

import br.hikarikun92.blogbackendheroku.factory.UserCredentialsFactory.Companion.USER_CREDENTIALS_2
import br.hikarikun92.blogbackendheroku.security.JwtTokenProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class UserServiceTest {
    @Mock
    private lateinit var repository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Mock
    private lateinit var tokenProvider: JwtTokenProvider

    private lateinit var service: UserService

    @BeforeEach
    fun setup() {
        service = UserService(repository, passwordEncoder, tokenProvider)
    }

    @Test
    fun `login with non-existing username`() {
        val username = "wrong"
        `when`(repository.findCredentialsByUsername(username)).thenReturn(Mono.empty())

        val token: String? = service.login(username, "password")
            .block()
        assertNull(token)

        verify(repository).findCredentialsByUsername(username)
        verifyNoMoreInteractions(repository, passwordEncoder, tokenProvider)
    }

    @Test
    fun `login with wrong password`() {
        val username = USER_CREDENTIALS_2.user.username
        val password = "wrong-pwd"
        `when`(repository.findCredentialsByUsername(username)).thenReturn(Mono.just(USER_CREDENTIALS_2))
        `when`(passwordEncoder.matches(password, USER_CREDENTIALS_2.password)).thenReturn(false)

        val token: String? = service.login(username, password)
            .block()
        assertNull(token)

        verify(repository).findCredentialsByUsername(username)
        verify(passwordEncoder).matches(password, USER_CREDENTIALS_2.password)
        verifyNoMoreInteractions(repository, passwordEncoder, tokenProvider)
    }

    @Test
    fun `login with success`() {
        val username = USER_CREDENTIALS_2.user.username
        val password = "correct-pwd"
        val expectedToken = UUID.randomUUID().toString()
        `when`(repository.findCredentialsByUsername(username)).thenReturn(Mono.just(USER_CREDENTIALS_2))
        `when`(passwordEncoder.matches(password, USER_CREDENTIALS_2.password)).thenReturn(true)
        `when`(tokenProvider.generateToken(username)).thenReturn(expectedToken)

        val token: String = service.login(username, password)
            .block()!!
        assertEquals(expectedToken, token)

        verify(repository).findCredentialsByUsername(username)
        verify(passwordEncoder).matches(password, USER_CREDENTIALS_2.password)
        verify(tokenProvider).generateToken(username)
        verifyNoMoreInteractions(repository, passwordEncoder, tokenProvider)
    }
}
