package br.hikarikun92.blogbackendheroku.factory

import br.hikarikun92.blogbackendheroku.comment.Comment
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import java.time.LocalDateTime

class CommentFactory {
    companion object {
        val COMMENT_1 = Comment(
            1,
            "Example comment 1",
            "Praesent sapien leo, viverra sed.",
            LocalDateTime.of(2021, 1, 1, 18, 42, 32),
            USER_2
        )
        val COMMENT_2 = Comment(2, "Great article", "Nice example!", LocalDateTime.of(2021, 2, 28, 7, 38, 12), USER_3)

        val COMMENT_3 =
            Comment(
                3,
                "Nulla sit amet ante in",
                "Curabitur ut maximus augue. Nunc luctus nibh risus.",
                LocalDateTime.of(2021, 7, 15, 15, 43, 41),
                USER_3
            )

        val COMMENT_4 =
            Comment(
                4,
                "Maecenas non sapien a elit",
                "Integer pulvinar nunc elit, eu interdum nisi ornare.",
                LocalDateTime.of(2021, 7, 18, 9, 53, 34),
                USER_2
            )

        val COMMENT_5 = Comment(
            5,
            "Curabitur viverra blandit finibus",
            "Nullam maximus risus vel urna mattis sollicitudin. Curabitur.",
            LocalDateTime.of(2021, 8, 1, 11, 28, 39),
            USER_3
        )
    }
}
