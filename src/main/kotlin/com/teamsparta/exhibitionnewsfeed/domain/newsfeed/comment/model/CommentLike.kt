package com.teamsparta.exhibitionnewsfeed.domain.newsfeed.comment.model

import com.teamsparta.exhibitionnewsfeed.domain.user.model.User
import jakarta.persistence.*

@Entity
class CommentLike(
    @ManyToOne
    val comment: Comment,

    @ManyToOne
    val user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}