package br.hikarikun92.blogbackendheroku.post.rest.dto

import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto

data class PostReadDto(val id: Int, val title: String, val body: String, val user: UserReadDto)