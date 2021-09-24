package br.hikarikun92.blogbackendheroku.user

data class UserCredentials(val user: User, val password: String, val roles: Set<String>)