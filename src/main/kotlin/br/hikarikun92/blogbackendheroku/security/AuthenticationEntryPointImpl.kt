package br.hikarikun92.blogbackendheroku.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class AuthenticationEntryPointImpl: ServerAuthenticationEntryPoint {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthenticationEntryPointImpl::class.java)
    }

    override fun commence(exchange: ServerWebExchange, ex: AuthenticationException): Mono<Void> = Mono.fromRunnable {
        logger.error("Responding with unauthorized error. Message - ${ex.message}")
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
    }
}