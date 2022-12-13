package br.hikarikun92.blogbackendheroku.post.rest

import br.hikarikun92.blogbackendheroku.post.rest.dto.CreatePostDto
import br.hikarikun92.blogbackendheroku.post.rest.dto.PostByIdDto
import br.hikarikun92.blogbackendheroku.post.rest.dto.PostByUserDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI

@RestController
class PostRestController(private val facade: PostRestFacade) {
    @GetMapping("users/{userId}/posts")
    fun findByUserId(@PathVariable userId: Int): Flux<PostByUserDto> = facade.findByUserId(userId)

    @GetMapping("posts/{id}")
    fun findById(@PathVariable id: Int): Mono<ResponseEntity<PostByIdDto>> = facade.findById(id)
        .map { ResponseEntity.ok(it) }
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))

    @PostMapping("posts")
    fun create(@RequestBody dto: CreatePostDto): Mono<ResponseEntity<Void>> = facade.create(dto)
        .map { ResponseEntity.created(URI.create("/posts/${it}")).build() }
}
