package br.hikarikun92.blogbackendheroku.post.rest.dto

import br.hikarikun92.blogbackendheroku.comment.rest.dto.CommentReadDto
import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class PostByUserDto(
    val id: Int,
    val title: String,
    val body: String,
    @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") val publishedDate: LocalDateTime
)

data class PostByIdDto(
    val id: Int,
    val title: String,
    val body: String,
    @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val publishedDate: LocalDateTime,
    val user: UserReadDto,
    val comments: List<CommentReadDto>
)
