package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.post.model

import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import jakarta.persistence.*

@Entity
class PostLike (
    @ManyToOne
    val post: Post,

    @ManyToOne
    val user : User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}