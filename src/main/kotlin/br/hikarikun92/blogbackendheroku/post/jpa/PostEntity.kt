package br.hikarikun92.blogbackendheroku.post.jpa

import br.hikarikun92.blogbackendheroku.user.jpa.UserEntity
import org.springframework.data.relational.core.mapping.Table
import javax.persistence.*

@Entity(name = "Post")
@Table("post")
data class PostEntity(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:Column(nullable = false, length = 50)
    var title: String? = null,

    @field:Column(nullable = false, columnDefinition = "text")
    var body: String? = null,

    @field:ManyToOne
    @field:JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity? = null,

    //comments
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostEntity
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id ?: 0
}
