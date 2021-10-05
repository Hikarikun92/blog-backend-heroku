package br.hikarikun92.blogbackendheroku.comment.rest.dto

import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CommentReadDto(
    val id: Int,
    val title: String,
    val body: String,
    @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    val publishedDate: LocalDateTime,
    val user: UserReadDto
)
