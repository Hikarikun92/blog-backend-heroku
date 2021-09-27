package br.hikarikun92.blogbackendheroku.security

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import reactor.core.publisher.Mono

//Security implementation based on https://ichi.pro/pt/autenticacao-jwt-no-spring-boot-webflux-114902343763911
@EnableReactiveMethodSecurity
class RestSecurityConfiguration {
    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        authenticationConverter: ServerAuthenticationConverter
    ): SecurityWebFilterChain {
        //Create an authentication filter with the converter defined in JwtServerAuthenticationConverter. Once the
        //converter has performed its validations and conversions, the authentication manager will simply return
        //the authentication itself, as there are no further validations and operations.
        val authenticationWebFilter = AuthenticationWebFilter(ReactiveAuthenticationManager { Mono.just(it) })
        authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter)

        return http.cors()
            .and()
            .csrf().disable()
            .logout().disable()
            .exceptionHandling().authenticationEntryPoint(AuthenticationEntryPointImpl())
            .and()
            .authorizeExchange()
            .anyExchange().permitAll() //TODO restrict access
            .and()
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
