package br.hikarikun92.blogbackendheroku.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class JwtAuthenticationManager(
    private val tokenProvider: JwtTokenProvider,
    private val userDetailsService: ReactiveUserDetailsService
) : ReactiveAuthenticationManager {
    //Use the token previously retrieved by the ServerAuthenticationConverter and retrieve the user associated to it
    override fun authenticate(authentication: Authentication): Mono<Authentication> =
        Mono.just(authentication)
            //Retrieve the username from the token
            .map { tokenProvider.getUsernameFromJwt(it.credentials as String) }
            //Convert it to UserDetails
            .flatMap { userDetailsService.findByUsername(it) }
            //Finally, build the real authentication used by Spring Security
            .map { UsernamePasswordAuthenticationToken(it, null, it.authorities) }
}