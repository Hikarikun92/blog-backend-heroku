package br.hikarikun92.blogbackendheroku.post

import br.hikarikun92.blogbackendheroku.post.jpa.PostEntity
import br.hikarikun92.blogbackendheroku.post.jpa.PostJpaRepository
import br.hikarikun92.blogbackendheroku.user.User
import br.hikarikun92.blogbackendheroku.user.jpa.UserEntity
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class PostRepository(private val jpaRepository: PostJpaRepository) {
    fun findByUserId(userId: Int): Flux<Post> = jpaRepository.findByUserId(userId)
        .map { it.toPost() }

    private fun PostEntity.toPost(): Post = Post(id, title!!, body!!, user!!.toUser())

    private fun UserEntity.toUser(): User = User(id, username!!)

    fun findById(id: Int): Mono<Post> = jpaRepository.findById(id)
        .map { it.toPost() }
}
