package br.hikarikun92.blogbackendheroku.user.jpa

import org.springframework.data.relational.core.mapping.Table
import javax.persistence.*

@Entity(name = "User")
@Table("user")
data class UserEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:Column(nullable = false)
    var username: String? = null,

    @field:OneToOne(fetch = FetchType.LAZY, optional = false)
    @field:JoinColumn(name = "user_id", nullable = false)
    var credentials: UserCredentialsEntity? = null,

    @field:ElementCollection
    @field:CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id", nullable = false)])
    var roles: Set<String>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserEntity
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id ?: 0
}