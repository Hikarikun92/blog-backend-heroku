package br.hikarikun92.blogbackendheroku.security

import br.hikarikun92.blogbackendheroku.user.User
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    val user: User,
    private var password: String?,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails, CredentialsContainer {
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getUsername(): String = user.username

    override fun getPassword(): String? = password

    override fun eraseCredentials() {
        password = null
    }

    override fun isEnabled(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}