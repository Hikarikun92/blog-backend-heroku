package br.hikarikun92.blogbackendheroku.user.rest

import br.hikarikun92.blogbackendheroku.user.rest.dto.LoginDto
import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping
class UserRestController(private val facade: UserRestFacade) {
    @GetMapping("users")
    fun findAll(): Flux<UserReadDto> = facade.findAll()

    @PostMapping("login")
    fun login(@RequestBody dto: LoginDto): Mono<ResponseEntity<String>> = facade.login(dto)
        .map { ResponseEntity.ok(it) }
        .switchIfEmpty(
            Mono.just(
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.WWW_AUTHENTICATE, "Bearer") // realm="api" -> what happens?
                    .build()
            )
        )
}