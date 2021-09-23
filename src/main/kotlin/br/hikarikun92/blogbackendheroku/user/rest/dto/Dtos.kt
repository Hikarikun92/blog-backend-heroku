package br.hikarikun92.blogbackendheroku.user.rest.dto

import br.hikarikun92.blogbackendheroku.user.User

data class UserReadDto(val id: Int, val username: String)

fun User.toReadDto() = UserReadDto(id!!, username)
