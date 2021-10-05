package br.hikarikun92.blogbackendheroku.comment

import br.hikarikun92.blogbackendheroku.user.User
import java.time.LocalDateTime

data class Comment(val id: Int?, val title: String, val body: String, val publishedDate: LocalDateTime, val user: User)
