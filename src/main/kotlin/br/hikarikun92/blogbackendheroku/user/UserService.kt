package br.hikarikun92.blogbackendheroku.user

import br.hikarikun92.blogbackendheroku.security.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: JwtTokenProvider
) {
    fun findAll(): Flux<User> = repository.findAll()

    fun login(username: String, password: String): Mono<String> {
        return repository.findCredentialsByUsername(username)
            .filter { passwordEncoder.matches(password, it.password) }
            .map { tokenProvider.generateToken(username) }
    }
}
