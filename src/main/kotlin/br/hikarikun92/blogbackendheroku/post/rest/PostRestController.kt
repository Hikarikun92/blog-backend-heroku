package br.hikarikun92.blogbackendheroku.post.rest

import br.hikarikun92.blogbackendheroku.post.rest.dto.PostByIdDto
import br.hikarikun92.blogbackendheroku.post.rest.dto.PostByUserDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class PostRestController(private val facade: PostRestFacade) {
    @GetMapping("users/{userId}/posts")
    fun findByUserId(@PathVariable userId: Int): Flux<PostByUserDto> = facade.findByUserId(userId)

    @GetMapping("posts/{id}")
    fun findById(@PathVariable id: Int): Mono<ResponseEntity<PostByIdDto>> = facade.findById(id)
        .map { ResponseEntity.ok(it) }
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
}
