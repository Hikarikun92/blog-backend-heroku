package br.hikarikun92.blogbackendheroku.post.jpa

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux

interface PostJpaRepository : R2dbcRepository<PostEntity, Int> {
    @Query("select * from Post p where p.user_id = :userId")
    fun findByUserId(userId: Int): Flux<PostEntity>
}