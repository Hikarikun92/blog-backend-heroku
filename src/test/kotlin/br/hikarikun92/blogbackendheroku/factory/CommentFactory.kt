package br.hikarikun92.blogbackendheroku.factory

import br.hikarikun92.blogbackendheroku.comment.Comment
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3

class CommentFactory {
    companion object {
        val COMMENT_1 = Comment(1, "Example comment 1", "Praesent sapien leo, viverra sed.", USER_2)
        val COMMENT_2 = Comment(2, "Great article", "Nice example!", USER_3)

        val COMMENT_3 =
            Comment(3, "Nulla sit amet ante in", "Curabitur ut maximus augue. Nunc luctus nibh risus.", USER_3)

        val COMMENT_4 =
            Comment(4, "Maecenas non sapien a elit", "Integer pulvinar nunc elit, eu interdum nisi ornare.", USER_2)

        val COMMENT_5 = Comment(
            5, "Curabitur viverra blandit finibus", "Nullam maximus risus vel urna mattis sollicitudin. Curabitur.",
            USER_3
        )
    }
}
