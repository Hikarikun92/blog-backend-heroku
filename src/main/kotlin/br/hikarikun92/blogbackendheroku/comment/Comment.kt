package br.hikarikun92.blogbackendheroku.comment

import br.hikarikun92.blogbackendheroku.user.User

data class Comment(val id: Int?, val title: String, val body: String, val user: User)
