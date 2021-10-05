package br.hikarikun92.blogbackendheroku.post

import br.hikarikun92.blogbackendheroku.comment.Comment
import br.hikarikun92.blogbackendheroku.user.User
import java.time.LocalDateTime

data class Post(
    val id: Int?,
    val title: String,
    val body: String,
    val publishedDate: LocalDateTime,
    val user: User,
    val comments: Set<Comment>? = null
)