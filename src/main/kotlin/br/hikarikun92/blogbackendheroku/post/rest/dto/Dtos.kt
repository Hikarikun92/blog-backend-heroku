package br.hikarikun92.blogbackendheroku.post.rest.dto

import br.hikarikun92.blogbackendheroku.comment.rest.dto.CommentReadDto
import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto

data class PostByUserDto(val id: Int, val title: String, val body: String)

data class PostByIdDto(
    val id: Int,
    val title: String,
    val body: String,
    val user: UserReadDto,
    val comments: List<CommentReadDto>
)
