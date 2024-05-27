package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model

import com.teamsparta.exhibitionnewsfeed.domain.user.model.Users
import jakarta.persistence.*

@Entity
class PostTag (
    @ManyToOne
    val post: Post,

    @ManyToOne
    val hashTag : HashTag
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}