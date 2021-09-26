package br.hikarikun92.blogbackendheroku.user.jpa

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "UserCredentials")
@Table(name = "user_credentials")
data class UserCredentialsEntity(
    @field:Id
    var id: Int? = null,

    @field:Column(nullable = false)
    var password: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserCredentialsEntity
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id ?: 0
}
