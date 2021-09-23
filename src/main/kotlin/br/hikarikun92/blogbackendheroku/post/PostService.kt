package br.hikarikun92.blogbackendheroku.post

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PostService(private val repository: PostRepository) {
    fun findByUserId(userId: Int): Flux<Post> = repository.findByUserId(userId)

    fun findById(id: Int): Mono<Post> = repository.findById(id)
}
