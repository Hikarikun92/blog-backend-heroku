package br.hikarikun92.blogbackendheroku.user.rest

import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("users")
class UserRestController(private val facade: UserRestFacade) {
    @GetMapping
    fun findAll(): Flux<UserReadDto> = facade.findAll()
}