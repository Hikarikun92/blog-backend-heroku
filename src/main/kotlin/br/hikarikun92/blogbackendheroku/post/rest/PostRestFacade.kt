package br.hikarikun92.blogbackendheroku.post.rest

import br.hikarikun92.blogbackendheroku.comment.rest.dto.CommentReadDto
import br.hikarikun92.blogbackendheroku.post.PostService
import br.hikarikun92.blogbackendheroku.post.rest.dto.PostByIdDto
import br.hikarikun92.blogbackendheroku.post.rest.dto.PostByUserDto
import br.hikarikun92.blogbackendheroku.user.User
import br.hikarikun92.blogbackendheroku.user.rest.dto.UserReadDto
import br.hikarikun92.blogbackendheroku.user.rest.dto.toReadDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PostRestFacade(private val service: PostService) {
    fun findByUserId(userId: Int): Flux<PostByUserDto> = service.findByUserId(userId)
        .map { PostByUserDto(it.id!!, it.title, it.body) }

    fun findById(id: Int): Mono<PostByIdDto> = service.findById(id)
        .map {
            val userCache: MutableMap<Int, UserReadDto> = mutableMapOf()

            val author: UserReadDto = it.user.toReadDto()
            userCache[author.id] = author

            val comments: List<CommentReadDto> = it.comments!!.map {
                val user: User = it.user
                val userDto: UserReadDto = userCache.computeIfAbsent(user.id!!) { user.toReadDto() }

                CommentReadDto(it.id!!, it.title, it.body, userDto)
            }

            PostByIdDto(it.id!!, it.title, it.body, author, comments)
        }
}
