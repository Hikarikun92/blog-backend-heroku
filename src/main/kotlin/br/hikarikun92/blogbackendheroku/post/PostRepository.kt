package br.hikarikun92.blogbackendheroku.post

import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.Post.Companion.POST
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.User.Companion.USER
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records.PostRecord
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records.UserRecord
import br.hikarikun92.blogbackendheroku.user.User
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.SelectConditionStep
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class PostRepository(private val dsl: DSLContext) {
    fun findByUserId(userId: Int): Flux<Post> {
        val query: SelectConditionStep<Record> = dsl.select()
            .from(POST)
            .join(USER).on(POST.USER_ID.eq(USER.ID))
            .where(POST.USER_ID.eq(userId))

        //Cache for avoiding creating multiple, identical users
        val userReference = Reference<User>()

        return Flux.from(query)
            .map { it.toPost(userReference) }
    }

    private fun Record.toPost(userReference: Reference<User>): Post {
        val user: User = userReference.get {
            val userRecord: UserRecord = into(USER)
            User(userRecord.id, userRecord.username!!)
        }

        val postRecord: PostRecord = into(POST)
        return Post(postRecord.id, postRecord.title!!, postRecord.body!!, user)
    }

    private class Reference<T : Any> {
        private lateinit var value: T

        fun get(supplier: () -> T): T {
            if (!this::value.isInitialized) {
                value = supplier()
            }

            return value
        }
    }
}