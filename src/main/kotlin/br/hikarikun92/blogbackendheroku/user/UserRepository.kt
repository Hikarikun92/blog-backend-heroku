package br.hikarikun92.blogbackendheroku.user

import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.User.Companion.USER
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class UserRepository(private val dsl: DSLContext) {
    fun findAll(): Flux<User> = Flux.from(dsl.selectFrom(USER))
        .map { User(it.id, it.username!!) }
}
