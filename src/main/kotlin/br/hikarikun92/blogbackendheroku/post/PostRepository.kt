package br.hikarikun92.blogbackendheroku.post

import br.hikarikun92.blogbackendheroku.comment.Comment
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.Comment.Companion.COMMENT
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.Post.Companion.POST
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.User.Companion.USER
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records.CommentRecord
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records.PostRecord
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.records.UserRecord
import br.hikarikun92.blogbackendheroku.user.User
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.SelectConditionStep
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import br.hikarikun92.blogbackendheroku.persistence.jooq.tables.User as UserTable

@Repository
class PostRepository(private val dsl: DSLContext) {
    companion object {
        private val AUTHOR: UserTable = USER.`as`("author")
        private val COMMENTER: UserTable = USER.`as`("commenter")
    }

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

    private class Reference<T : Any> {
        private lateinit var value: T

        fun get(supplier: () -> T): T {
            if (!this::value.isInitialized) {
                value = supplier()
            }

            return value
        }
    }

    private fun Record.toPost(userReference: Reference<User>): Post {
        val user: User = userReference.get {
            into(USER).toUser()
        }

        return into(POST).toPost(user)
    }

    private fun UserRecord.toUser(): User = User(id, username!!)

    private fun PostRecord.toPost(user: User, comments: Set<Comment>? = null) =
        Post(id, title!!, body!!, user, comments)

    fun findById(id: Int): Mono<Post> {
        val query: SelectConditionStep<Record> = dsl.select()
            .from(POST)
            .join(AUTHOR).on(AUTHOR.ID.eq(POST.USER_ID))
            .leftJoin(COMMENT).on(COMMENT.POST_ID.eq(POST.ID))
            .leftJoin(COMMENTER).on(COMMENT.USER_ID.eq(COMMENTER.ID))
            .where(POST.ID.eq(id))

        return Flux.from(query)
            .collectList()
            .flatMap {
                if (it.isEmpty()) {
                    return@flatMap Mono.empty<Post>()
                }

                val firstRecord: Record = it.first()

                val author: User = firstRecord.into(AUTHOR).toUser()

                val comments = mutableSetOf<Comment>()
                it.forEach {
                    val commentRecord: CommentRecord = it.into(COMMENT)
                    if (commentRecord.postId != null) { //If postId is null, it means there were no comments
                        val commenter: User = it.into(COMMENTER).toUser()
                        comments.add(Comment(commentRecord.id, commentRecord.title!!, commentRecord.body!!, commenter))
                    }
                }

                val post: Post = firstRecord.into(POST).toPost(author, comments)
                Mono.just(post)
            }
    }
}