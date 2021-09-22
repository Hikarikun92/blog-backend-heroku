package br.hikarikun92.blogbackendheroku.factory

import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_2
import br.hikarikun92.blogbackendheroku.factory.UserFactory.Companion.USER_3
import br.hikarikun92.blogbackendheroku.post.Post

class PostFactory {
    companion object {
        val POST_1 = Post(1, "Example post no. 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse placerat.", USER_2)
        val POST_2 = Post(2, "Another example post", "Integer malesuada lorem non nunc.", USER_2)
        val POST_3 = Post(3, "Writing example applications in Kotlin", "Kotlin methods are fun", USER_3)
    }
}