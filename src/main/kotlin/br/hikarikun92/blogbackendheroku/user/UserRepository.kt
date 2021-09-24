package br.hikarikun92.blogbackendheroku.user

import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.User.Companion.USER
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.UserCredentials.Companion.USER_CREDENTIALS
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.UserRoles.Companion.USER_ROLES
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records.UserRecord
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records.UserRolesRecord
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.SelectConditionStep
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class UserRepository(private val dsl: DSLContext) {
    fun findAll(): Flux<User> = Flux.from(dsl.selectFrom(USER))
        .map { User(it.id, it.username!!) }

    fun findCredentialsByUsername(username: String): Mono<UserCredentials> {
        val query: SelectConditionStep<Record> = dsl.select()
            .from(USER)
            .join(USER_CREDENTIALS).on(USER_CREDENTIALS.USER_ID.eq(USER.ID))
            .leftJoin(USER_ROLES).on(USER_ROLES.USER_ID.eq(USER.ID))
            .where(USER.USERNAME.eq(username))

        return Flux.from(query)
            .collectList()
            .flatMap {
                if (it.isEmpty()) {
                    return@flatMap Mono.empty<UserCredentials>()
                }

                val firstRecord: Record = it.first()
                val userRecord: UserRecord = firstRecord.into(USER)

                val user = User(userRecord.id, userRecord.username!!)

                val roles = mutableSetOf<String>()
                it.forEach {
                    val rolesRecord: UserRolesRecord = it.into(USER_ROLES)
                    if (rolesRecord.userId != null) { //If userId is null, it means there were no roles
                        roles.add(rolesRecord.roles!!)
                    }
                }

                val credentialsRecord = firstRecord.into(USER_CREDENTIALS)
                val credentials = UserCredentials(user, credentialsRecord.password!!, roles)
                return@flatMap Mono.just(credentials)
            }
    }
}
