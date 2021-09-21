package br.hikarikun92.blogbackendheroku.factory

import br.hikarikun92.blogbackendheroku.user.User

class UserFactory {
    companion object {
        val USER_1 = User(1, "Administrator")
        val USER_2 = User(2, "John Doe")
        val USER_3 = User(3, "Mary Doe")
    }
}
