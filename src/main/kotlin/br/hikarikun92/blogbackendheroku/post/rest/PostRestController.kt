package br.hikarikun92.blogbackendheroku.post.rest

import br.hikarikun92.blogbackendheroku.post.rest.dto.PostReadDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class PostRestController(private val facade: PostRestFacade) {
    @GetMapping("users/{userId}/posts")
    fun findById(@PathVariable userId: Int): Flux<PostReadDto> = facade.findByUserId(userId)
}