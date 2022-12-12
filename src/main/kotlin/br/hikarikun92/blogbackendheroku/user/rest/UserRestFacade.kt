package br.hikarikun92.blogbackendheroku.user.rest

import br.hikarikun92.blogbackendheroku.user.UserService
import br.hikarikun92.blogbackendheroku.user.rest.dto.LoginDto
import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto
import br.hikarikun92.blogbackendheroku.user.rest.dto.toReadDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserRestFacade(private val service: UserService) {
    fun findAll(): Flux<UserReadDto> = service.findAll()
        .map { it.toReadDto() }

    fun login(dto: LoginDto): Mono<String> = service.login(dto.username, dto.password)
}
