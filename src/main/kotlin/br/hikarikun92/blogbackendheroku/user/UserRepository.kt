package br.hikarikun92.blogbackendheroku.user

import br.hikarikun92.blogbackendheroku.user.jpa.UserJpaRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class UserRepository(private val jpaRepository: UserJpaRepository) {
    fun findAll(): Flux<User> = jpaRepository.findAll()
        .map { User(it.id, it.username!!) }

    fun findCredentialsByUsername(username: String): Mono<UserCredentials> =
        jpaRepository.findCredentialsByUsername(username)
            .map {
                val user = User(it.id, it.username!!)
                UserCredentials(user, it.credentials!!.password!!, it.roles!!)
            }
}
