package br.hikarikun92.blogbackendheroku.user.rest

import br.hikarikun92.blogbackendheroku.user.UserService
import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class UserRestFacade(private val service: UserService) {
    fun findAll(): Flux<UserReadDto> = service.findAll()
        .map { UserReadDto(it.id!!, it.username) }
}