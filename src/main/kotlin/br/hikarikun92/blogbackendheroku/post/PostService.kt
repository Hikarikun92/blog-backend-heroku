package br.hikarikun92.blogbackendheroku.post

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class PostService(private val repository: PostRepository) {
    fun findByUserId(userId: Int): Flux<Post> = repository.findByUserId(userId)
}