package br.hikarikun92.blogbackendheroku.factory

import br.hikarikun92.blogbackendheroku.factory.CommentFactory.Companion.COMMENT_1
import br.hikarikun92.blogbackendheroku.factory.CommentFactory.Companion.COMMENT_2
import br.hikarikun92.blogbackendheroku.factory.CommentFactory.Companion.COMMENT_3
import br.hikarikun92.blogbackendheroku.factory.CommentFactory.Companion.COMMENT_4
import br.hikarikun92.blogbackendheroku.factory.CommentFactory.Companion.COMMENT_5
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import br.hikarikun92.blogbackendheroku.post.Post
import java.time.LocalDateTime

class PostFactory {
    companion object {
        val POST_1 = Post(
            1,
            "Example post no. 1",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse placerat.",
            LocalDateTime.of(2021, 1, 1, 12, 3, 18),
            USER_2
        )
        val POST_1_WITH_COMMENTS = POST_1.copy(comments = setOf(COMMENT_1, COMMENT_2))

        val POST_2 = Post(
            2,
            "Another example post",
            "Integer malesuada lorem non nunc.",
            LocalDateTime.of(2021, 3, 15, 17, 53, 7),
            USER_2
        )
        val POST_2_WITH_COMMENTS = POST_2.copy(comments = setOf())

        val POST_3 = Post(
            3,
            "Writing example applications in Kotlin",
            "Kotlin methods are fun",
            LocalDateTime.of(2021, 7, 12, 23, 50, 46),
            USER_3
        )
        val POST_3_WITH_COMMENTS = POST_3.copy(comments = setOf(COMMENT_3, COMMENT_4, COMMENT_5))
    }
}
