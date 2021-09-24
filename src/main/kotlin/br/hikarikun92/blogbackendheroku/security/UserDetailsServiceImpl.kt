package br.hikarikun92.blogbackendheroku.security

import br.hikarikun92.blogbackendheroku.user.UserRepository
import org.springframework.context.annotation.Primary
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Primary
@Service
class UserDetailsServiceImpl(private val repository: UserRepository) : ReactiveUserDetailsService {
    override fun findByUsername(username: String): Mono<UserDetails> {
        return repository.findCredentialsByUsername(username)
            .map {
                val authorities: List<SimpleGrantedAuthority> = it.roles.map { SimpleGrantedAuthority(it) }
                UserDetailsImpl(it.user, it.password, authorities)
            }
    }
}