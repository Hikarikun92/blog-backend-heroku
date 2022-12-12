package br.hikarikun92.blogbackendheroku.post.rest

import br.hikarikun92.blogbackendheroku.comment.rest.dto.CommentReadDto
import br.hikarikun92.blogbackendheroku.post.Post
import br.hikarikun92.blogbackendheroku.post.PostService
import br.hikarikun92.blogbackendheroku.post.rest.dto.CreatePostDto
import br.hikarikun92.blogbackendheroku.post.rest.dto.PostByIdDto
import br.hikarikun92.blogbackendheroku.post.rest.dto.PostByUserDto
import br.hikarikun92.blogbackendheroku.security.UserDetailsImpl
import br.hikarikun92.blogbackendheroku.user.User
import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto
import br.hikarikun92.blogbackendheroku.user.rest.dto.toReadDto
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Clock
import java.time.LocalDateTime

@Service
class PostRestFacade(private val service: PostService, private val clock: Clock) {
    fun findByUserId(userId: Int): Flux<PostByUserDto> = service.findByUserId(userId)
        .map { PostByUserDto(it.id!!, it.title, it.body, it.publishedDate) }

    fun findById(id: Int): Mono<PostByIdDto> = service.findById(id)
        .map {
            val userCache: MutableMap<Int, UserReadDto> = mutableMapOf()

            val author: UserReadDto = it.user.toReadDto()
            userCache[author.id] = author

            val comments: List<CommentReadDto> = it.comments!!.map {
                val user: User = it.user
                val userDto: UserReadDto = userCache.computeIfAbsent(user.id!!) { user.toReadDto() }

                CommentReadDto(it.id!!, it.title, it.body, it.publishedDate, userDto)
            }

            PostByIdDto(it.id!!, it.title, it.body, it.publishedDate, author, comments)
        }

    //TODO restrict access to ROLE_USER
    fun create(dto: CreatePostDto): Mono<Int> =
        ReactiveSecurityContextHolder.getContext()
            .map { it.authentication.principal as UserDetailsImpl }
            .map { it.user }
            .flatMap {
                val post = Post(null, dto.title, dto.body, LocalDateTime.now(clock), it)
                service.create(post)
            }
}
