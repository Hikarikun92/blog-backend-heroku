package br.hikarikun92.blogbackendheroku.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtServerAuthenticationConverter(
    private val tokenProvider: JwtTokenProvider,
    private val userDetailsService: ReactiveUserDetailsService
) : ServerAuthenticationConverter {
    //Convert a given exchange (a.k.a. request) to an Authentication object, which will be used to restrict the user
    //based on their authorities
    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> =
        Mono.justOrEmpty(exchange)
            //Get the Authorization header, if any
            .flatMap { Mono.justOrEmpty(it.request.headers.getFirst(HttpHeaders.AUTHORIZATION)) }
            //If present, it must start with "Bearer "
            .filter { it.startsWith("Bearer ") }
            //If it's a Bearer token, remove the "Bearer " to use only the token itself
            .map { it.substring(7) }
            //Validate the token
            .filter { tokenProvider.validateToken(it) }
            //If valid, retrieve the username from it
            .map { tokenProvider.getUsernameFromJwt(it) }
            //Search for the corresponding user and convert it to UserDetails
            .flatMap { userDetailsService.findByUsername(it) }
            //Finally, if the user exists, generate an Authentication object containing the user and their authorities
            .map { UsernamePasswordAuthenticationToken(it, null, it.authorities) }
}
