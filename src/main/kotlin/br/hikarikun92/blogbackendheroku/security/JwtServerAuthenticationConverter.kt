package br.hikarikun92.blogbackendheroku.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtServerAuthenticationConverter(private val tokenProvider: JwtTokenProvider) : ServerAuthenticationConverter {
    //Convert a given exchange (a.k.a. request) to an Authentication object. In this case, this object will be an
    //intermediary one with a valid JWT token, that will later be used by a ReactiveAuthenticationManager to find the
    //corresponding user.
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
            //Finally, if valid, generate an Authentication object containing it. This will now be used by the
            //ReactiveAuthenticationManager.
            .map { UsernamePasswordAuthenticationToken(it, it) }
}