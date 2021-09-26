package br.hikarikun92.blogbackendheroku.user.jpa

import org.springframework.data.jpa.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface UserJpaRepository : R2dbcRepository<UserEntity, Int> {
    @Query("select u from User u join fetch u.credentials left join fetch u.roles where u.username = :username")
    fun findCredentialsByUsername(username: String): Mono<UserEntity>
}