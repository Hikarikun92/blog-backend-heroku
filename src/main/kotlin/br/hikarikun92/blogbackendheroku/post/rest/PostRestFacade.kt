package br.hikarikun92.blogbackendheroku.post.rest

import br.hikarikun92.blogbackendheroku.post.PostService
import br.hikarikun92.blogbackendheroku.post.rest.dto.PostReadDto
import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class PostRestFacade(private val service: PostService) {
    fun findByUserId(userId: Int): Flux<PostReadDto> = service.findByUserId(userId)
        .map { PostReadDto(it.id!!, it.title, it.body, it.user.let { UserReadDto(it.id!!, it.username) }) }
}