package br.hikarikun92.blogbackendheroku.factory

import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_1
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import br.hikarikun92.blogbackendheroku.user.UserCredentials
import br.hikarikun92.blogbackendheroku.user.UserRoles.Companion.ROLE_ADMIN
import br.hikarikun92.blogbackendheroku.user.UserRoles.Companion.ROLE_USER

class UserCredentialsFactory {
    companion object {
        val USER_CREDENTIALS_1 = UserCredentials(
            USER_1,
            "\$2a\$10\$2KOuXO8SUQGSsWhbDrAQoet9Ts8yJ4y853WYVHF0C3Y2Lv1YNacHS",
            setOf(ROLE_ADMIN, ROLE_USER)
        )
        val USER_CREDENTIALS_2 =
            UserCredentials(USER_2, "\$2a\$10\$bS.HuGI.l5pFEjfjDIjB2.3t9h62kRSi3exUTBhbs6vqrJouNTDh2", setOf(ROLE_USER))
        val USER_CREDENTIALS_3 =
            UserCredentials(USER_3, "\$2a\$10\$0K6JAr2YTe82YWid8dPNKeNQ3q73HVH4viQbAq.j.Z6pv3XItAO46", setOf())
    }
}
