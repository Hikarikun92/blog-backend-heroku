package br.hikarikun92.blogbackendheroku.comment.rest.dto

import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto

data class CommentReadDto(val id: Int, val title: String, val body: String, val user: UserReadDto)
