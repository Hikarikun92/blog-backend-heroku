package br.hikarikun92.blogbackendheroku.user

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class UserService(private val repository: UserRepository) {
    fun findAll(): Flux<User> = repository.findAll()
}
